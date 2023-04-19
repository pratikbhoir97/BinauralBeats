package com.example.binauralbeats.utils

import android.content.Intent
import com.example.binauralbeats.MusicServiceOld
import com.example.binauralbeats.R
import com.example.binauralbeats.databinding.ActivitySecondBinding
import kotlin.properties.Delegates

class CommonData {

    companion object {
        val imageList = arrayListOf<Int>(
            R.drawable.study,
            R.drawable.focused,
            R.drawable.creative,
            R.drawable.amnesia,
            R.drawable.love,
            R.drawable.meditation,
            R.drawable.moon,
            R.drawable.ptsd,
            R.drawable.underwater,
            R.drawable.atmosphere
        )

        val audioBeats = arrayListOf<Int>(
            R.raw.simply,
            R.raw.focussed,
            R.raw.creativity,
            R.raw.memory,
            R.raw.love,
            R.raw.relax,
            R.raw.sleep,
            R.raw.reduceanxiety,
            R.raw.underwater,
            R.raw.space
        )
        var nameList = arrayListOf<String>(
            "Study", "Focus", "Creativity", "Memory", "Love", "Relax", "Sleep", "Reduce Anxiety",
            "UnderWater", "Atmospheric"
        )

        var beatCurrent:String = ""


        //Music class data
        var isPlaying: Boolean = false

        lateinit var beatList: ArrayList<String>
        lateinit var beatImage: ArrayList<Int>
        lateinit var binding: ActivitySecondBinding


        lateinit var audioList: ArrayList<Int>
        var position by Delegates.notNull<Int>()
        lateinit var currentBeatName: String
        var currentImage by Delegates.notNull<Int>()
        var currentAudio by Delegates.notNull<Int>()

        var musicServiceOld: MusicServiceOld? = null
        lateinit var a : Intent
    }

}