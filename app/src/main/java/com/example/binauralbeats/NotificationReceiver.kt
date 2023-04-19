package com.example.binauralbeats

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.binauralbeats.utils.CommonData
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PLAY -> if (CommonData.isPlaying) pauseMusic() else playMusic()
              ApplicationClass.EXIT->{
                  CommonData.musicServiceOld!!.startFGServiceNotification()
                  CommonData.musicServiceOld=null
                   exitProcess(1)
               }
        }
    }


    private fun playMusic() {
        CommonData.isPlaying = true
        CommonData.musicServiceOld!!.mediaPlayer!!.start()
        CommonData.musicServiceOld!!.showNotification(R.drawable.pause)
        CommonData.binding.button.setImageResource(R.drawable.pause)
    }

    private fun pauseMusic() {
        CommonData.isPlaying = false
        CommonData.musicServiceOld!!.mediaPlayer!!.pause()
        CommonData.musicServiceOld!!.showNotification(R.drawable.play)
        CommonData.binding.button.setImageResource(R.drawable.play)


    }
}