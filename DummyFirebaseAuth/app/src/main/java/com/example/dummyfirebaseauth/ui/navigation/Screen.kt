package com.example.dummyfirebaseauth.ui.navigation

sealed class Screen(val route: String) {
    object SignIn : Screen("SignIn")
    object Profile : Screen("Profile")
    object UserList : Screen("UserList")
    object LiveDataExample : Screen("LiveDataExample")
    object MessageListScreen : Screen(": Screen(\"LiveDataExample\")")
    object GetPictureExampleScreen : Screen("GetPictureExampleScreen")
}