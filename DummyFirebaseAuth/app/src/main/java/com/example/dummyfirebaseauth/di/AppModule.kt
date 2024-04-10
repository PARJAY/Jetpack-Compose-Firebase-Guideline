package com.example.dummyfirebaseauth.di

import com.example.dummyfirebaseauth.data.repository.UserRepositoryImpl

interface AppModule {
    val userRepositoryImpl : UserRepositoryImpl
}