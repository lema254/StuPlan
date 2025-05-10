package com.example.stuplan

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Optional: any application-wide setup here
    }
}

annotation class HiltAndroidApp
