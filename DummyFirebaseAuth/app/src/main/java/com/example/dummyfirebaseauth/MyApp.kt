package com.example.dummyfirebaseauth

import android.app.Application
import com.example.dummyfirebaseauth.di.AppModule
import com.example.dummyfirebaseauth.di.AppModuleImpl

class MyApp: Application() {

    companion object {
        lateinit var appModule: AppModule
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl()
    }
}