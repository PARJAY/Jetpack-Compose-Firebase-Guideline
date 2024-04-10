package com.example.dummyfirebaseauth.di

import com.example.dummyfirebaseauth.data.repository.UserRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AppModuleImpl(
//    private val ioDispatcher: CoroutineDispatcher,
): AppModule {
    private val db = Firebase.firestore

    override val userRepositoryImpl: UserRepositoryImpl by lazy {
        UserRepositoryImpl(db)
    }
}