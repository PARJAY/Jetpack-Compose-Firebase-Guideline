package com.example.dummyfirebaseauth.ui.navigation

sealed class Screen(val route: String) {
    object SignInScreen : Screen("SignInScreen")
    object ProfileScreen : Screen("ProfileScreen")
    object MapsScreen : Screen("MapsScreen")
    object LocationGpsScreen : Screen("LocationGpsScreen")
    object UserListScreen : Screen("UserListScreen")

    object LiveDataExample : Screen("LiveDataExample")
    object MessageListScreen : Screen("MessageListScreen")
    object GetPictureExampleScreen : Screen("GetPictureExampleScreen")
}