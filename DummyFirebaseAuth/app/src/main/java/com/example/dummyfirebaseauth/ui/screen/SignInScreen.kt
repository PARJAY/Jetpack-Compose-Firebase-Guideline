package com.example.dummyfirebaseauth.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.dummyfirebaseauth.presentation.sign_in.SignInState
import com.example.dummyfirebaseauth.ui.navigation.Screen

@Composable
fun SignInScreen(
    state : SignInState,
    onSignInClick: () -> Unit,
    onNavigateTo: (String) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInErrorMessage) {
        state.signInErrorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ) {
        Button(onClick = { onSignInClick() }) {
            Text(text = "Sign In")
        }

        Button(onClick = { onNavigateTo(Screen.UserListScreen.route) }) {
            Text(text = "get firestore data")
        }

        Button(onClick = { onNavigateTo(Screen.LiveDataExample.route) }) {
            Text(text = "get firestore live data")
        }

        Button(onClick = { onNavigateTo(Screen.MessageListScreen.route) }) {
            Text(text = "To trial and error screen")
        }

        Button(onClick = { onNavigateTo(Screen.TrackUserLocationScreen.route) }) {
            Text(text = "To Track User Location Screen")
        }

        Button(onClick = { onNavigateTo(Screen.GetPictureExampleScreen.route) }) {
            Text(text = "get firebase storage file data")
        }

        Button(onClick = { onNavigateTo(Screen.MapsScreen.route) }) {
            Text(text = "to maps screen")
        }
    }
}