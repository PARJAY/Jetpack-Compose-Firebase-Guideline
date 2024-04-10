package com.example.dummyfirebaseauth.ui.component.navigation

sealed class Screen(val route: String) {
    object SignIn : Screen("SignIn")
    object Profile : Screen("Profile")
    object UserList : Screen("UserList")
    object GetPictureExampleScreen : Screen("GetPictureExampleScreen")
}