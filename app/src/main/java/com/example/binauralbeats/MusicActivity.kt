package com.example.binauralbeats

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.binauralbeats.AdMobAds.InterStitialAds
import com.example.binauralbeats.AdMobAds.NativeAndBannerAds
import com.example.binauralbeats.databinding.ActivitySecondBinding
import com.example.binauralbeats.utils.CommonData
import com.google.android.gms.ads.MobileAds
import kotlin.properties.Delegates


class MusicActivity : AppCompatActivity(), ServiceConnection {
    companion object {
        var isPlaying: Boolean = false

        lateinit var beatList: ArrayList<String>
        lateinit var beatImage: ArrayList<Int>
        lateinit var binding: ActivitySecondBinding


        lateinit var audioList: ArrayList<Int>
        var position by Delegates.notNull<Int>()
        lateinit var currentBeatName: String
        var currentImage by Delegates.notNull<Int>()
        var currentAudio by Delegates.notNull<Int>()

        var musicService: MusicService? = null
    }
 lateinit var seekBar: SeekBar
    lateinit var song: TextView
    lateinit var imageView: ImageView
    lateinit var button: ImageButton
    private lateinit var nativeAdContainer: RelativeLayout
    private lateinit var bannerAdFrameLayout: FrameLayout
    val nativeandBanner = NativeAndBannerAds()
    var interStitialAds = InterStitialAds()
    lateinit var activity: Activity

    override fun onBackPressed() {
        interStitialAds.showInterstitalAd(activity)
        super.onBackPressed()
    }

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        song = findViewById(R.id.songname)
        imageView = findViewById(R.id.profile_image)
        button = findViewById(R.id.button)
        seekBar=findViewById(R.id.volumeSeekbar)
        activity = this
        MobileAds.initialize(this) {}
        // Find the native and banner ad view container in the layout
        nativeAdContainer = findViewById(R.id.native_ad_container)
        bannerAdFrameLayout = findViewById(R.id.banner_ad_container)
        nativeandBanner.loadNativeOrBannerAds(nativeAdContainer,bannerAdFrameLayout,this)


        interStitialAds.loadInterStitialAds(this)



        currentBeatName = intent.getStringExtra("beatName").toString()
        CommonData.beatCurrent = currentBeatName;
        song.setText(currentBeatName)
        song.setSelected(true)



        currentImage = intent.getIntExtra("imageName", 0)
        imageView.setImageResource(currentImage)


        currentAudio = intent.getIntExtra("currentAudio", 0)
        position = intent.getIntExtra("position", 0)


        // Get the audio manager
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        // Set the maximum volume of the SeekBar to the maximum volume of the MediaPlayer:
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBar.max = maxVolume

        // Set the current volume of the SeekBar to the current volume of the MediaPlayer:
        val currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        seekBar.progress = currVolume

        // Add a SeekBar.OnSeekBarChangeListener to the SeekBar:
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do Nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do Nothing
            }
        })






        var isServiceCreated : Boolean= false
        val a = Intent(this, MusicService::class.java)
        bindService(a, this, BIND_AUTO_CREATE)

        binding.button.setOnClickListener {
            if (isServiceCreated==false){

                startService(a)
            }

            if (musicService!!.mediaPlayer == null) {
                Log.d("MusicActivity", "1st")
                musicService!!.mediaPlayer = MediaPlayer.create(this, currentAudio)
                musicService!!.mediaPlayer?.start()
                button.setImageResource(R.drawable.pause)
                musicService!!.showNotification(R.drawable.pause)
            } else if (musicService!!.mediaPlayer!!.isPlaying) {
                Log.d("MusicActivity", "2nd")

                button.setImageResource(R.drawable.play)
                musicService!!.mediaPlayer?.pause()
                musicService!!.showNotification(R.drawable.play)
            } else if (musicService!!.mediaPlayer?.isPlaying == false) {
                Log.d("MusicActivity", "3rd")
                button.setImageResource(R.drawable.pause)
                musicService!!.mediaPlayer?.start()
                musicService!!.showNotification(R.drawable.pause)
            }
        }
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        musicService!!.showNotification(R.drawable.play)
//        musicService!!.mediaPlayer=MediaPlayer.create(this, audioCurrent)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }
}