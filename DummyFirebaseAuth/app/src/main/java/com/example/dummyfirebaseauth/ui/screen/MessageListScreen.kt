package com.example.dummyfirebaseauth.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

data class Message(val sender: String, val content: String)

@Composable
fun MessageListScreen() {
    val messages = remember { mutableStateListOf<Message>() }

    // Add some initial messages
//    messages.add(Message("John", "Hello!"))
//    messages.add(Message("Jane", "Hi there!"))

    LazyColumn {
        items(messages) { message ->
            MessageItem(message = message) // Composable for displaying a message
        }

        item {
            Button(onClick = { messages.add(Message("You", "New message!")) }) {
                Text("Add Message")
            }
        }
    }
}

@Composable
fun MessageItem(message : Message) {
    Text("Sender : ${message.sender}")
    Text("Content : ${message.content}")


}
