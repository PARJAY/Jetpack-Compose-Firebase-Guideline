package com.example.dummyfirebaseauth.ui.component.user_list_dedicated

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dummyfirebaseauth.data.model.UserModel

@Composable
fun UserItem(
    user: UserModel,
    onUpdateAction : (UserModel) -> Unit,
    onDeleteAction : (String) -> Unit
) {
    Column (modifier = Modifier.padding(top = 8.dp)) {
        Text(text = user.id)
        Text(text = user.name)
        Text(text = user.address)
        Text(text = user.phone_number)

        Button(onClick = { onUpdateAction(user) }) {
            Text(text = "Edit")
        }

        Button(onClick = { onDeleteAction(user.id) }) {
            Text(text = "Delete")
        }
    }
}