package com.example.dummyfirebaseauth.di

import com.example.dummyfirebaseauth.data.repository.UserRepositoryImpl
import com.google.firebase.storage.FirebaseStorage

interface AppModule {
    val userRepositoryImpl : UserRepositoryImpl
    val storage : FirebaseStorage
}