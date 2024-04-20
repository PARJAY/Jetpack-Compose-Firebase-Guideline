package com.example.dummyfirebaseauth.di

import com.example.dummyfirebaseauth.data.repository.LiveDataTraningRepository
import com.example.dummyfirebaseauth.data.repository.UserRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.ktx.storage

class AppModuleImpl : AppModule {
    override val db = Firebase.firestore
    override val storage = com.google.firebase.ktx.Firebase.storage("gs://nofwa-indonesia.appspot.com")

    override val userRepositoryImpl: UserRepositoryImpl by lazy {
        UserRepositoryImpl(db)
    }

    override val liveDataTraningRepository: LiveDataTraningRepository by lazy {
        LiveDataTraningRepository(db)
    }

}