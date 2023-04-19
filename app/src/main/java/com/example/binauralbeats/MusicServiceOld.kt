package com.example.binauralbeats

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.binauralbeats.ApplicationClass.Companion.CHANNEL_ID
import com.example.binauralbeats.utils.CommonData

class MusicServiceOld:Service() {
    lateinit var player: MediaPlayer
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat

    override fun onBind(intent: Intent?): IBinder? {
        mediaSession= MediaSessionCompat(baseContext,"My Music")
        return myBinder
    }

    inner class MyBinder : Binder() {
        fun currentService(): MusicServiceOld {
            return this@MusicServiceOld
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    //    createNotificationChannel()
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

  @SuppressLint("SuspiciousIndentation")
  fun showNotification(pauseBtn:Int){
      val playIntent=Intent(baseContext,NotificationReceiver::class.java).setAction(ApplicationClass.PLAY)
//      val playPendingIntent=PendingIntent.getBroadcast(baseContext,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT)
      var playPendingIntent: PendingIntent? = null
      playPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          PendingIntent.getBroadcast(this, 0, playIntent, PendingIntent.FLAG_MUTABLE)
      } else {
          PendingIntent.getBroadcast(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)
      }

     val exitIntent=Intent(baseContext,NotificationReceiver::class.java).setAction(ApplicationClass.EXIT)
//      val exitPendingIntent=PendingIntent.getBroadcast(baseContext,0,exitIntent,PendingIntent.FLAG_UPDATE_CURRENT)
      var exitPendingIntent: PendingIntent? = null
      exitPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          PendingIntent.getBroadcast(this, 0, exitIntent, PendingIntent.FLAG_MUTABLE)
      } else {
          PendingIntent.getBroadcast(this, 0, exitIntent, PendingIntent.FLAG_UPDATE_CURRENT)
      }


      val notification=NotificationCompat.Builder(baseContext,ApplicationClass.CHANNEL_ID)
          .setContentTitle(CommonData.beatCurrent)
        //  .setContentText(CommonData.beatCurrent)
          .setSmallIcon(R.drawable.baseline_music_note_24)
          .setLargeIcon(BitmapFactory.decodeResource(resources,CommonData.currentImage))
          .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
          .setPriority(NotificationCompat.PRIORITY_LOW)
          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
          .setOnlyAlertOnce(true)
          .setAutoCancel(true)
          .setOngoing(true)
          .addAction(pauseBtn,"play",playPendingIntent)
         .addAction(R.drawable.baseline_exit_to_app_24,"exit",exitPendingIntent)
          .build()

        startForeground(13,notification)
  }
    }






