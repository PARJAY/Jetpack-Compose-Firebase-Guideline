package com.example.dummyfirebaseauth.presentation.user_list

import com.example.dummyfirebaseauth.data.model.UserModel

data class UserListScreenUiState(
    val isLoading: Boolean = false,
    val users: List<UserModel> = emptyList(),
    val errorMessage: String? = null,
    val userToBeUpdated: UserModel? = null,
    val isShowDeleteUserDialog: Boolean = false,
)

