package com.example.binauralbeats

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.example.binauralbeats.ApplicationClass.Companion.CHANNEL_ID
import com.example.binauralbeats.utils.CommonData
import kotlin.properties.Delegates

class MusicService:Service() {
    lateinit var player: MediaPlayer
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    inner class MyBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
//        player = MediaPlayer.create(this, R.raw.space)
//        player.setLooping(true)
//        player.start()

        return START_STICKY
    }

    fun startFGServiceNotification(){
        val notificationIntent = Intent(this, MusicActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
//            .setContentText(input)
            .setSmallIcon(R.drawable.pause)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
//            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}


