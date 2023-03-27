package com.example.binauralbeats

import android.app.Application
import android.app.NotificationChannel
import android.os.Build

class ApplicationClass:Application() {

    companion object {
        const val CHANNEL_ID = "binaural_beats"
        const val PLAY="play"
        const val PAUSE="pause"
        const val EXIT="exit"



    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel=NotificationChannel()

        }

    }
}