package com.example.binauralbeats

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PLAY -> if (MusicActivity.isPlaying) pauseMusic() else playMusic()
              ApplicationClass.EXIT->{
                   MusicActivity.musicService!!.startFGServiceNotification()
                   MusicActivity.musicService=null
                   exitProcess(1)
               }
        }
    }


    private fun playMusic() {
        MusicActivity.isPlaying = true
        MusicActivity.musicService!!.mediaPlayer!!.start()
        MusicActivity.musicService!!.showNotification(R.drawable.pause)
        MusicActivity.binding.button.setImageResource(R.drawable.pause)


    }

    private fun pauseMusic() {
        MusicActivity.isPlaying = false
        MusicActivity.musicService!!.mediaPlayer!!.pause()
        MusicActivity.musicService!!.showNotification(R.drawable.play)
        MusicActivity.binding.button.setImageResource(R.drawable.play)


    }
}