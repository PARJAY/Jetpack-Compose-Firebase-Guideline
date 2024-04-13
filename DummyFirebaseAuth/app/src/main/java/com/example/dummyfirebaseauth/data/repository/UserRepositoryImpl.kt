package com.example.dummyfirebaseauth.data.repository

import android.util.Log
import com.example.dummyfirebaseauth.common.FirebaseResult
import com.example.dummyfirebaseauth.common.INTERNET_ISSUE
import com.example.dummyfirebaseauth.common.USER_COLLECTION
import com.example.dummyfirebaseauth.data.model.UserModel
import com.example.dummyfirebaseauth.tools.FirebaseHelper.Companion.fetchSnapshotToUserModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(private val db : FirebaseFirestore) : UserRepository {

    private var listenerRegistration : ListenerRegistration? = null

    override suspend fun getUsers(callback: (FirebaseResult<List<UserModel>>) -> Unit) {
        val userModelSnapshots = mutableListOf<UserModel>()

        listenerRegistration = db.collection(USER_COLLECTION).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                callback(FirebaseResult.Failure(IllegalStateException(INTERNET_ISSUE)))
                return@addSnapshotListener
            }

            snapshot!!.documentChanges.forEach { change ->
                val userModel = fetchSnapshotToUserModel(change.document)
                when (change.type) {
                    DocumentChange.Type.ADDED -> userModelSnapshots.add(userModel)
                    DocumentChange.Type.MODIFIED -> {
                        val index = userModelSnapshots.indexOfFirst { it.id == change.document.id }
                        if (index != -1) userModelSnapshots[index] = userModel
                    }

                    DocumentChange.Type.REMOVED -> userModelSnapshots.removeAll { it.id == change.document.id }
                }
                Log.d("REPOSITORY: ", "Data In -> ${change.type} - ${change.document}")
            }

            callback(FirebaseResult.Success(userModelSnapshots))
        }
    }

    // dipake kalau nggak pengen nerima data realtime lagi
    fun detachListener() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }

    // kayaknya nggak bakal pernah dipake
//    suspend fun getUserById(userId: String): UserModel? {
//        val documentSnapshot = db.collection(USER_COLLECTION).document(userId).get().await()
//        return documentSnapshot.toObject(UserModel::class.java)
//    }

    suspend fun addUser(user: UserModel) {
        db.collection(USER_COLLECTION).add(user).await()
    }

    suspend fun updateUser(userId: String, updatedUser: UserModel) {
        db.collection(USER_COLLECTION).document(userId).set(updatedUser).await()
    }

    suspend fun deleteUser(userId: String) {
        db.collection(USER_COLLECTION).document(userId).delete().await()
    }
}