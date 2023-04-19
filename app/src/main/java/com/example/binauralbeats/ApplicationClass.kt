package com.example.binauralbeats

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.binauralbeats.utils.CommonData

class ApplicationClass:Application() {

    companion object {
        const val CHANNEL_ID = "binaural_beats"
        const val PLAY="play"
        const val EXIT="exit"


    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = getString(R.string.app_name)

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }

    override fun onTerminate() {
        stopService(CommonData.a)
        super.onTerminate()
    }

}