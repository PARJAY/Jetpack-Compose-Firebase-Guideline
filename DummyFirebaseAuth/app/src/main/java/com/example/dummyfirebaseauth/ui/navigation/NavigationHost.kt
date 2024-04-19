package com.example.dummyfirebaseauth.ui.navigation

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dummyfirebaseauth.MyApp
import com.example.dummyfirebaseauth.presentation.viewModelFactory
import com.example.dummyfirebaseauth.presentation.sign_in.GoogleAuthUiClient
import com.example.dummyfirebaseauth.presentation.sign_in.SignInState
import com.example.dummyfirebaseauth.presentation.sign_in.SignInViewModel
import com.example.dummyfirebaseauth.presentation.user_list.UserListScreenViewModel
import com.example.dummyfirebaseauth.ui.screen.GetPictureExampleScreen
import com.example.dummyfirebaseauth.ui.screen.LiveDataExampleScreen
import com.example.dummyfirebaseauth.ui.screen.MessageListScreen
import com.example.dummyfirebaseauth.ui.screen.ProfileScreen
import com.example.dummyfirebaseauth.ui.screen.SignInScreen
import com.example.dummyfirebaseauth.ui.screen.UserListScreen
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun NavigationHost(
    lifecycleOwner: LifecycleOwner
){
    val context = LocalContext.current

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val lifecycleScope = lifecycleOwner.lifecycleScope

    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(navController = navController, startDestination = Screen.SignIn.route) {
        composable(Screen.SignIn.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state: SignInState by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null)
                    navController.navigate("profile")
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(context, "Sign in Success", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.Profile.route)
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                onNavigateTo = {
                    navController.navigate(it)
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen (
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(context, "Signed Out", Toast.LENGTH_LONG).show()

                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.UserList.route) {
            val userListScreenVM: UserListScreenViewModel = viewModel(
                factory = viewModelFactory { UserListScreenViewModel(MyApp.appModule.userRepositoryImpl) }
            )
            val userListScreenUiState = userListScreenVM.state.collectAsStateWithLifecycle().value
            val userListScreenEffectFlow = userListScreenVM.effect

            UserListScreen(userListScreenUiState, userListScreenEffectFlow, userListScreenVM::onEvent)
        }

        composable(Screen.GetPictureExampleScreen.route) {
            GetPictureExampleScreen()
        }

        composable(Screen.LiveDataExample.route) {
            LiveDataExampleScreen()
        }

        composable(Screen.MessageListScreen.route) {
            MessageListScreen()
        }


    }
}