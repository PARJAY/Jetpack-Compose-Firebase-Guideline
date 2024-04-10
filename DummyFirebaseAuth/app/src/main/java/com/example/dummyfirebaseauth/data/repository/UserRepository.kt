package com.example.dummyfirebaseauth.data.repository

import com.example.dummyfirebaseauth.common.FirebaseResult
import com.example.dummyfirebaseauth.data.model.UserModel

interface UserRepository {
    suspend fun getUsers(callback: (FirebaseResult<List<UserModel>>) -> Unit)
}
