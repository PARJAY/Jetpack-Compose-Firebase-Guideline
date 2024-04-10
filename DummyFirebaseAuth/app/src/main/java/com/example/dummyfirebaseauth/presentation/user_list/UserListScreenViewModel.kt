package com.example.dummyfirebaseauth.presentation.user_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyfirebaseauth.common.FirebaseResult
import com.example.dummyfirebaseauth.data.model.UserModel
import com.example.dummyfirebaseauth.data.repository.UserRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserListScreenViewModel(private val userRepositoryImpl: UserRepositoryImpl) : ViewModel() {

    private val _state: MutableStateFlow<UserListScreenUiState> =
        MutableStateFlow(UserListScreenUiState())
    val state: StateFlow<UserListScreenUiState> = _state.asStateFlow()

    private val _effect: Channel<UserListScreenSideEffects> = Channel()
    val effect = _effect.receiveAsFlow()

    init { onEvent(UserEvent.GetUsers) }

    private fun setEffect(builder: () -> UserListScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    private fun setState(newState: UserListScreenUiState) {
        _state.value = newState
    }

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.GetUsers -> getUsers()
            is UserEvent.DeleteUser -> deleteUser(event.userId)
            is UserEvent.AddUser -> addUser(event.newUser)
            is UserEvent.UpdateUser -> updateUser(event.updatedUser)
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true) // Update loading state

            userRepositoryImpl.getUsers { firebaseResult ->
                when (firebaseResult) {
                    is FirebaseResult.Failure -> {
                        setState(_state.value.copy(isLoading = false))
                        Log.d("VIEWMODEL: ", "${firebaseResult.exception.message}")
                        setEffect { UserListScreenSideEffects.ShowSnackBarMessage(firebaseResult.exception.message ?: "Error fetching users") }
                    }
                    is FirebaseResult.Success -> {
                        Log.d("VIEWMODEL: ", "${firebaseResult.data}")
                        _state.value = _state.value.copy(isLoading = false, users = firebaseResult.data)
                        setEffect { UserListScreenSideEffects.ShowSnackBarMessage(message = "User list data loaded successfully") }
                    }
                }
            }
        }
    }

    private fun deleteUser(userId: String) {
        viewModelScope.launch {
            setState(_state.value.copy(isLoading = true))

            try {
                userRepositoryImpl.deleteUser(userId)
                setState(_state.value.copy(isLoading = false))
                setEffect { UserListScreenSideEffects.ShowSnackBarMessage(message = "User deleted successfully") }
            } catch (e: Exception) {
                setState(_state.value.copy(isLoading = false, errorMessage = e.localizedMessage))
                setEffect { UserListScreenSideEffects.ShowSnackBarMessage(e.message ?: "Error fetching users") }
            }
        }
    }

    private fun updateUser(updatedUser: UserModel) {
        viewModelScope.launch {
            setState(_state.value.copy(isLoading = true))

            try {
                userRepositoryImpl.updateUser(updatedUser.id, updatedUser)
                setState(_state.value.copy(isLoading = false))
                setEffect { UserListScreenSideEffects.ShowSnackBarMessage(message = "User updated successfully") }
            } catch (e: Exception) {
                setState(_state.value.copy(isLoading = false, errorMessage = e.localizedMessage))
                setEffect { UserListScreenSideEffects.ShowSnackBarMessage(e.message ?: "Error fetching users") }
            }
        }
    }

    private fun addUser(newUser: UserModel) {
        viewModelScope.launch {
            setState(_state.value.copy(isLoading = true))

            try {
                userRepositoryImpl.addUser(newUser)
                setState(_state.value.copy(isLoading = false))
                setEffect { UserListScreenSideEffects.ShowSnackBarMessage(message = "User added successfully") }
            } catch (e: Exception) {
                setState(_state.value.copy(isLoading = false, errorMessage = e.localizedMessage))
                setEffect { UserListScreenSideEffects.ShowSnackBarMessage(e.message ?: "Error fetching users") }
            }
        }
    }
}