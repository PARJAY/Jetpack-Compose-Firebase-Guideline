package com.example.dummyfirebaseauth.ui.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dummyfirebaseauth.MyApp
import com.example.dummyfirebaseauth.common.DUMMY_STORAGE_LOCATION
import com.example.dummyfirebaseauth.tools.FirebaseHelper.Companion.getFileFromFirebaseStorage
import com.example.dummyfirebaseauth.tools.FirebaseHelper.Companion.uploadImageToFirebaseStorage

@Composable
fun GetPictureExampleScreen() {
    val context = LocalContext.current

    var imageURI by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }

    getFileFromFirebaseStorage(
        fileReference = "WhatsApp Image 2023-12-09 at 20.32.55.jpeg",
        onSuccess = { imageURI = it},
        onError = {}
    )

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage (
            model = imageURI,
            contentDescription = "Profile picture",
            modifier = Modifier.size(300.dp),
            contentScale = ContentScale.Crop
        )

        Button(onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "Pick a file from gallery")
        }

        AsyncImage (
            model = selectedImageUri,
            contentDescription = "Profile picture",
            modifier = Modifier.size(300.dp),
            contentScale = ContentScale.Crop
        )

        Button(
            onClick = {
                if (selectedImageUri?.path!!.isNotEmpty()) {
                    uploadImageToFirebaseStorage(
                        DUMMY_STORAGE_LOCATION,
                        selectedImageUri!!,
                        onSuccess = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        },
                        onError = {
                            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(context, "Select an Image", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Send a file")
        }
    }
}