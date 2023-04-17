package com.example.binauralbeats.utils

import com.example.binauralbeats.R

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
    }
}