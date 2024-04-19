package com.example.dummyfirebaseauth.tools

import android.net.Uri
import android.util.Log
import com.example.dummyfirebaseauth.MyApp
import com.example.dummyfirebaseauth.data.model.UserModel
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.storage.FirebaseStorage

class FirebaseHelper {
    companion object {
        fun fetchSnapshotToUserModel(queryDocumentSnapshot : QueryDocumentSnapshot) : UserModel {
            return UserModel(
                id = queryDocumentSnapshot.id,
                name = queryDocumentSnapshot.getString("name") ?: "",
                address = queryDocumentSnapshot.getString("address") ?: "",
                phone_number = queryDocumentSnapshot.getString("phone_number") ?: ""
            )
        }

        fun getFileFromFirebaseStorage (
            fileReference : String,
            onSuccess: (Uri) -> Unit,
            onError: (Exception) -> Unit
        ) {
            MyApp.appModule.storage.reference.child(fileReference).downloadUrl.addOnSuccessListener { uri ->
                Log.d("Firebase Helper: ", "download Success : $uri")
                onSuccess(uri)
            }.addOnFailureListener { exception ->
                onError(exception)
                Log.d("Firebase Helper: ", "download error : $exception")
            }
        }

        fun uploadImageToFirebaseStorage(
            userIdForFileReference : String,
            file: Uri,
            onSuccess: (String) -> Unit,
            onError: (Exception) -> Unit
        ) {
            val fileName = file.path?.substringAfterLast("/")

            MyApp.appModule.storage.getReference("$userIdForFileReference/$fileName").putFile(file).addOnSuccessListener { snapshot ->
                snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    Log.d("Firebase Helper: ", "upload Success : $uri")
                    onSuccess(uri.toString())
                }?.addOnFailureListener { exception ->
                    Log.d("Firebase Helper: ", "upload Failed : $exception")
                    onError(exception)
                }
            }.addOnFailureListener { exception ->
                Log.d("Firebase Helper: ", "Error : $exception")
                onError(exception)
            }
        }
    }
}