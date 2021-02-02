package ru.skokova.android_academy

import android.app.Application
import android.content.Context

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}