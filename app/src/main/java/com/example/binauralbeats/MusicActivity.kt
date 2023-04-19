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
import java.time.Duration
import kotlin.properties.Delegates


class MusicActivity : AppCompatActivity(), ServiceConnection {

 lateinit var VolumControllerSeekBar: SeekBar
    lateinit var song: TextView
    lateinit var imageView: ImageView
    lateinit var button: ImageButton

    //Ads variables
    private lateinit var nativeAdContainer: RelativeLayout
    private lateinit var bannerAdFrameLayout: FrameLayout
    val nativeandBanner = NativeAndBannerAds()
    var interStitialAds = InterStitialAds()
    lateinit var activity: Activity

    var isServiceCreated : Boolean= false


    override fun onDestroy() {
        if (CommonData.musicServiceOld!!.mediaPlayer != null){
            if (CommonData.musicServiceOld!!.mediaPlayer!!.isPlaying) {
                CommonData.musicServiceOld!!.mediaPlayer?.pause()
            }
        }
        stopService(CommonData.a)

        interStitialAds.showInterstitalAd(activity)
        super.onDestroy()
    }

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonData.binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(CommonData.binding.root)

        song = findViewById(R.id.songname)
        imageView = findViewById(R.id.profile_image)
        button = findViewById(R.id.button)
        VolumControllerSeekBar=findViewById(R.id.volumeSeekbar)
        activity = this
        MobileAds.initialize(this) {}
        // Find the native and banner ad view container in the layout
        nativeAdContainer = findViewById(R.id.native_ad_container)
        bannerAdFrameLayout = findViewById(R.id.banner_ad_container)
        nativeandBanner.loadNativeOrBannerAds(nativeAdContainer,bannerAdFrameLayout,this)
        interStitialAds.loadInterStitialAds(this)



        CommonData.a = Intent(this, MusicServiceOld::class.java)
        bindService(CommonData.a, this, BIND_AUTO_CREATE)


        CommonData.currentBeatName = intent.getStringExtra("beatName").toString()
        CommonData.beatCurrent = CommonData.currentBeatName;
        song.setText(CommonData.currentBeatName)
        song.setSelected(true)



        CommonData.currentImage = intent.getIntExtra("imageName", 0)
        imageView.setImageResource(CommonData.currentImage)


        CommonData.currentAudio = intent.getIntExtra("currentAudio", 0)
        CommonData.position = intent.getIntExtra("position", 0)


        // Get the audio manager
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        // Set the maximum volume of the SeekBar to the maximum volume of the MediaPlayer:
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        VolumControllerSeekBar.max = maxVolume

        // Set the current volume of the SeekBar to the current volume of the MediaPlayer:
        val currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        VolumControllerSeekBar.progress = currVolume

        // Add a SeekBar.OnSeekBarChangeListener to the SeekBar:
        VolumControllerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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


        CommonData.binding.button.setOnClickListener {
            if (isServiceCreated==false){
                isServiceCreated = true;
                startService(CommonData.a)
            }
            if (CommonData.musicServiceOld!!.mediaPlayer == null) {
                Log.d("MusicActivity", "1st")
//                Toast.makeText(activity,"1st method",Toast.LENGTH_LONG).show()

                CommonData. musicServiceOld!!.mediaPlayer = MediaPlayer.create(this, CommonData.currentAudio)
                CommonData.musicServiceOld!!.mediaPlayer?.start()
                CommonData.musicServiceOld!!.mediaPlayer?.isLooping=true

                button.setImageResource(R.drawable.pause)
                CommonData.musicServiceOld!!.showNotification(R.drawable.pause)
            } else if (CommonData.musicServiceOld!!.mediaPlayer!!.isPlaying) {
                Log.d("MusicActivity", "2nd")
//                Toast.makeText(activity,"2nd method",Toast.LENGTH_LONG).show()

                button.setImageResource(R.drawable.play)
                CommonData.musicServiceOld!!.mediaPlayer?.pause()
                CommonData.musicServiceOld!!.showNotification(R.drawable.play)
            } else if (CommonData.musicServiceOld!!.mediaPlayer?.isPlaying == false) {
                Log.d("MusicActivity", "3rd")
//                Toast.makeText(activity,"3rd method",Toast.LENGTH_LONG).show()

                button.setImageResource(R.drawable.pause)
                CommonData.musicServiceOld!!.mediaPlayer?.start()
                CommonData.musicServiceOld!!.showNotification(R.drawable.pause)
            }
        }
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicServiceOld.MyBinder
        CommonData.musicServiceOld = binder.currentService()
        CommonData.musicServiceOld!!.showNotification(R.drawable.play)
//        musicService!!.mediaPlayer=MediaPlayer.create(this, audioCurrent)
    }
    override fun onServiceDisconnected(name: ComponentName?) {
        CommonData.musicServiceOld = null
    }
}