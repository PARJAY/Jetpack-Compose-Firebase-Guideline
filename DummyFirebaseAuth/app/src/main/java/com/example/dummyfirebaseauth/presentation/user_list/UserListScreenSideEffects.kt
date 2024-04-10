package com.example.dummyfirebaseauth.presentation.user_list

sealed class UserListScreenSideEffects {
    data class ShowSnackBarMessage(val message: String) : UserListScreenSideEffects()
}