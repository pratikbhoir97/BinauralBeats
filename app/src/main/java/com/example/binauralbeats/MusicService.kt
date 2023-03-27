package com.example.binauralbeats

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.Parcelable
import com.example.binauralbeats.utils.CommonData
import kotlin.properties.Delegates

class MusicService:Service() {
    lateinit var player: MediaPlayer
private var myBinder=MyBinder()
    var mediaPlayer:MediaPlayer?=null

    override fun onBind(intent: Intent?): IBinder? {
      return myBinder
    }
    inner class MyBinder:Binder(){
        fun currentService():MusicService{
            return this@MusicService
        }
    }


    }

 /*   override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, R.raw.space)
        player.setLooping(true)
        player.start()
        return START_STICKY
    }
 */


