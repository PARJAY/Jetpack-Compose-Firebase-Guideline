package com.example.dummyfirebaseauth.di

import com.example.dummyfirebaseauth.data.repository.UserRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

interface AppModule {
    val db : FirebaseFirestore
    val userRepositoryImpl : UserRepositoryImpl
    val storage : FirebaseStorage
}