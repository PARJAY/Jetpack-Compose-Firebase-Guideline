package com.example.dummyfirebaseauth.presentation.user_list

import com.example.dummyfirebaseauth.data.model.UserModel

sealed class UserEvent {
    object GetUsers : UserEvent()
    data class DeleteUser(val userId: String) : UserEvent()
    data class UpdateUser(val updatedUser: UserModel) : UserEvent()
    data class AddUser(val newUser: UserModel) : UserEvent()
}