package com.example.shahrukhtask

import android.app.Application
import android.content.Context

class AppApplication:Application() {

    companion object {

        lateinit var appContext: Context
        lateinit var mInstance: AppApplication

        fun getInstance(): AppApplication {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        appContext = this
    }

}