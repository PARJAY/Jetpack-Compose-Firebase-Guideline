package com.example.dummyfirebaseauth.data.repository

import android.util.Log
import com.example.dummyfirebaseauth.common.INTERNET_ISSUE
import com.example.dummyfirebaseauth.common.LIVEDATA_TRAINING
import com.example.dummyfirebaseauth.ui.screen.LiveDataTrainingModel
import com.example.dummyfirebaseauth.ui.screen.fetchSnapshotToLiveDataTraining
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class LiveDataTraningRepository(val db : FirebaseFirestore) {
    private var listenerRegistration : ListenerRegistration? = null

    fun getLiveDataTrainingData(
        errorCallback: (Exception) -> Unit,
        addDataCallback: (LiveDataTrainingModel) -> Unit,
        updateDataCallback: (LiveDataTrainingModel) -> Unit,
        deleteDataCallback: (documentId : String) -> Unit
    ) {


        listenerRegistration = db.collection(LIVEDATA_TRAINING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                errorCallback(IllegalStateException(INTERNET_ISSUE))
                return@addSnapshotListener
            }

            snapshot!!.documentChanges.forEach { change ->
                val userModel = fetchSnapshotToLiveDataTraining(change.document)
                when (change.type) {
                    DocumentChange.Type.ADDED -> addDataCallback(userModel)
                    DocumentChange.Type.MODIFIED -> updateDataCallback(userModel)
                    DocumentChange.Type.REMOVED -> deleteDataCallback(userModel.id)
                }
                Log.d("LDES Repo: ", "Data In -> ${change.type} - ${change.document}")
            }
        }
    }

    fun detachListener() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }
}