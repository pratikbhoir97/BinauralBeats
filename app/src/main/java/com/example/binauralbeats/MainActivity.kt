package com.example.binauralbeats

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binauralbeats.utils.CommonData

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView1: RecyclerView
    lateinit var adapter: MusicAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView1 = findViewById(R.id.Recyclerview)
        recyclerView1.layoutManager = GridLayoutManager(this, 2)
        adapter = MusicAdapter(CommonData.imageList, CommonData.nameList, CommonData.audioBeats, this@MainActivity)
        recyclerView1.adapter = adapter


    }


}