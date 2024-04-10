package com.example.dummyfirebaseauth.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dummyfirebaseauth.common.SIDE_EFFECTS_KEY
import com.example.dummyfirebaseauth.data.model.UserModel
import com.example.dummyfirebaseauth.presentation.user_list.UserEvent
import com.example.dummyfirebaseauth.presentation.user_list.UserListScreenSideEffects
import com.example.dummyfirebaseauth.presentation.user_list.UserListScreenUiState
import com.example.dummyfirebaseauth.ui.component.EmptyComponent
import com.example.dummyfirebaseauth.ui.component.LoadingComponent
import com.example.dummyfirebaseauth.ui.component.ShowDialogUserInput
import com.example.dummyfirebaseauth.ui.component.user_list_dedicated.UserItem
import com.example.dummyfirebaseauth.ui.theme.DummyFirebaseAuthTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

@Composable
fun UserListScreen(
    userListScreenUiState: UserListScreenUiState,
    userListScreenEffectFlow : Flow<UserListScreenSideEffects>,
    onUserEvent: (UserEvent) -> Unit,
) {
    val openUserDataDialog = remember { mutableStateOf(false) }
    val selectedUser = remember { mutableStateOf<UserModel?>(null) }

    ShowSnackbar(userListScreenEffectFlow)

    ShowDialogUserInput(
        openUserDataDialog.value,
        onDismissRequest = { openUserDataDialog.value = false },
        onConfirmation = {
            if (selectedUser.value == null) onUserEvent(UserEvent.AddUser(it))
            else {
                onUserEvent(UserEvent.UpdateUser(it))
                selectedUser.value = null
            }
            openUserDataDialog.value = false
        },
        selectedUser.value
    )

    when {
        userListScreenUiState.isLoading -> LoadingComponent()

        !userListScreenUiState.isLoading && userListScreenUiState.users.isEmpty() -> EmptyComponent()

        else -> {
            LazyColumn {
                items(userListScreenUiState.users) { user ->
                    UserItem(
                        user,
                        onUpdateAction = {
                            // open user edit dialog, set selected user data (this function callback usermodel data)
                            openUserDataDialog.value = true
                            selectedUser.value = it
                        },
                        onDeleteAction = {
                            onUserEvent(UserEvent.DeleteUser(it))
                        }
                    )
                }
            }
        }
    }

    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = { openUserDataDialog.value = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 16.dp,
                    end = 16.dp
                )
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add New Report")
        }
    }
}

@Composable
fun ShowSnackbar(userListScreenEffectFlow : Flow<UserListScreenSideEffects>,) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        userListScreenEffectFlow.onEach { effect ->
            when (effect) {
                is UserListScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }
            }
        }.collect()
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OutletListScreenNoDataPreview() {
    DummyFirebaseAuthTheme {
        Surface {
            UserListScreen(
                userListScreenUiState = UserListScreenUiState(
                    users = listOf(
                        UserModel("randomId1", "parjay", "jlbtnvi no 1 btln", "086969860596"),
                        UserModel("randomId2", "parjay", "jlbtnvi no 1 btln", "086969860596"),
                        UserModel("randomId3", "parjay", "jlbtnvi no 1 btln", "086969860596")
                    ),
                ),
                userListScreenEffectFlow = flow {
                    emit(UserListScreenSideEffects.ShowSnackBarMessage("this is a snackbar message"))
                },
                onUserEvent = {  },
            )
        }
    }
}