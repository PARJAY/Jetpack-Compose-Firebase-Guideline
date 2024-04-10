package com.example.dummyfirebaseauth.tools

import com.example.dummyfirebaseauth.data.model.UserModel
import com.google.firebase.firestore.QueryDocumentSnapshot

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
    }
}