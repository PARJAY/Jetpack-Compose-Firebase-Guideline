package com.example.dummyfirebaseauth.ui.component.user_list_dedicated

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dummyfirebaseauth.data.model.UserModel

@Composable
fun UserItem(
    user: UserModel,
    onUpdateAction : (UserModel) -> Unit,
    onDeleteAction : (String) -> Unit
) {
    val userModel by remember {
        mutableStateOf(user)
    }

    Column (modifier = Modifier.padding(top = 8.dp)) {
        Text(text = userModel.id)
        Text(text = userModel.name)
        Text(text = userModel.address)
        Text(text = userModel.phone_number)

        Button(onClick = { onUpdateAction(userModel) }) {
            Text(text = "Edit")
        }

        Button(onClick = { onDeleteAction(userModel.id) }) {
            Text(text = "Delete")
        }
    }
}