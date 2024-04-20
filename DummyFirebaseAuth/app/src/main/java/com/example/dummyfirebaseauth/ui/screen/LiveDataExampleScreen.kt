package com.example.dummyfirebaseauth.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.dummyfirebaseauth.MyApp
import com.example.dummyfirebaseauth.common.INTERNET_ISSUE
import com.example.dummyfirebaseauth.common.LIVEDATA_TRAINING
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QueryDocumentSnapshot

data class LiveDataTrainingModel(
    val id : String = "",
    val name : String = "",
    val nim : Int = 0,
)

@SuppressLint("MutableCollectionMutableState")
@Composable
fun LiveDataExampleScreen() {
    val contex = LocalContext.current

    val liveDataTrainingModels = remember {
        mutableStateListOf<LiveDataTrainingModel>()
    }

    LaunchedEffect(Unit) { // Launch effect after composition
        MyApp.appModule.liveDataTraningRepository.getLiveDataTrainingData(
            errorCallback = {
                Toast.makeText(contex, "error : $it", Toast.LENGTH_LONG).show()
                Log.d("Screen", "error : $it")
            },
            addDataCallback = {
                liveDataTrainingModels.add(it)
                Log.d("Screen", "added to screen : $it")

            },
            updateDataCallback = { updatedData ->

                val index = liveDataTrainingModels.indexOfFirst { it.id == updatedData.id }
                if (index != -1) liveDataTrainingModels[index] = updatedData
                Log.d("Screen", "updated to screen : $updatedData")
            },
            deleteDataCallback = { documentId: String ->
                liveDataTrainingModels.removeAll { it.id == documentId }
                Log.d("Screen", "deleted from screen : id = $documentId")
            }
        )
    }

    LazyColumn {
        items(liveDataTrainingModels) {
            Text("name : ${it.name}")
            Text("nim : ${it.nim}")
            Spacer(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

fun fetchSnapshotToLiveDataTraining(queryDocumentSnapshot : QueryDocumentSnapshot) : LiveDataTrainingModel {
    return LiveDataTrainingModel(
        id = queryDocumentSnapshot.id,
        name = queryDocumentSnapshot.getString("name") ?: "",
        nim = (queryDocumentSnapshot.getLong("NIM") ?: 0).toInt()
    )
}