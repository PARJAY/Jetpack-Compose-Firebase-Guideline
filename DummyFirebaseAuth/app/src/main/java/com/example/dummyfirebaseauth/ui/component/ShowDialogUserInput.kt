package com.example.dummyfirebaseauth.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.dummyfirebaseauth.data.model.UserModel
import com.example.dummyfirebaseauth.ui.theme.DummyFirebaseAuthTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDialogUserInput(
    isOpen : Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: (UserModel) -> Unit,
    currentUserData : UserModel? = UserModel()
) {
    if (isOpen) {
        var name by remember { mutableStateOf(currentUserData?.name ?: "") }
        var address by remember { mutableStateOf(currentUserData?.address ?: "") }
        var phoneNumber by remember { mutableStateOf(currentUserData?.phone_number ?: "") }

        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "Input Nama Anda") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},

                        label = { Text(text = "Masukkan Nama") },
                        supportingText = { Text(text = "*required") },

                        isError = name.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = {address = it},

                        label = { Text(text = "Masukkan Alamat") },
                        supportingText = { Text(text = "*required") },

                        isError = address.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { newValue : String ->
                            phoneNumber = newValue
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                        label = { Text(text = "Masukkan Telpon") },
                        supportingText = { Text(text = "*required") },

                        isError = phoneNumber.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = { onConfirmation(UserModel( currentUserData?.id ?: "", name, address, phoneNumber)) }) {
                    Text("Confirm")
                }
            },

            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text("Cancel")
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogInputOpenOutletPreview() {
    DummyFirebaseAuthTheme {
        Surface {
            ShowDialogUserInput(
                true,
                onDismissRequest = {},
                onConfirmation = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogInputNotOpenOutletPreview() {
    DummyFirebaseAuthTheme {
        Surface {
            ShowDialogUserInput(
                false,
                onDismissRequest = {},
                onConfirmation = {}
            )
        }
    }
}