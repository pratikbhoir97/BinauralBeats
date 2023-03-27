package com.example.binauralbeats

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates


class MusicActivity : AppCompatActivity(), ServiceConnection {
    companion object {
        lateinit var beatList: ArrayList<String>
        lateinit var beatImage: ArrayList<Int>



        lateinit var audioList: ArrayList<Int>
        var position by Delegates.notNull<Int>()
        lateinit var beatCurrent: String

        var imageCurrent by Delegates.notNull<Int>()
        var audioCurrent by Delegates.notNull<Int>()


        var musicService: MusicService? = null
    }
    lateinit var song: TextView
    lateinit var imageView: ImageView
    lateinit var button: ImageButton

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        song = findViewById(R.id.songname)
        imageView = findViewById(R.id.profile_image)
        button = findViewById(R.id.button)


        val intent = intent
        val bundle = intent.extras
        beatList =
            (bundle!!.getParcelableArrayList<Parcelable>("nameList") as java.util.ArrayList<String>?)!!

        beatCurrent = intent.getStringExtra("beatName").toString()
        song.setText(beatCurrent)
        song.setSelected(true)


        beatImage =
            (bundle!!.getParcelableArrayList<Parcelable>("imageList") as java.util.ArrayList<Int>?)!!
        imageCurrent = intent.getIntExtra("imageName", 0)
        imageView.setImageResource(imageCurrent)

        audioList =
            (bundle!!.getParcelableArrayList<Parcelable>("audioBeats") as java.util.ArrayList<Int>?)!!
        audioCurrent = intent.getIntExtra("audioList", 0)
        position = intent.getIntExtra("position", 0)

        val a = Intent(this, MusicService::class.java)
        bindService(a, this, BIND_AUTO_CREATE)
        startService(a)


        button.setOnClickListener {


            if (musicService!!.mediaPlayer == null) {
             //  musicService!!. mediaPlayer = MediaPlayer.create(this, audioCurrent)
              musicService!!.  mediaPlayer?.start()
            } else if (musicService!!.mediaPlayer!!.isPlaying) {
                button.setBackgroundResource(R.drawable.play)
              musicService!!.  mediaPlayer?.pause()
            } else if (musicService!!.mediaPlayer?.isPlaying == true) {
                button.setBackgroundResource(R.drawable.pause)
               musicService!!. mediaPlayer?.start()
            }


        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        musicService!!.mediaPlayer=MediaPlayer.create(this, audioCurrent)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }
}