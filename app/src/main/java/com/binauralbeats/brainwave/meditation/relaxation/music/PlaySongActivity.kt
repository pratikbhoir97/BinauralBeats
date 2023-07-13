package com.binauralbeats.brainwave.meditation.relaxation.music

import android.app.Activity
import android.media.AudioManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.binauralbeats.brainwave.meditation.relaxation.music.AdMobAds.InterStitialAds
import com.binauralbeats.brainwave.meditation.relaxation.music.AdMobAds.NativeAndBannerAds
import com.binauralbeats.brainwave.meditation.relaxation.music.utils.CommonData
import com.google.android.gms.ads.MobileAds

class PlaySongActivity : AppCompatActivity() {
    lateinit var VolumControllerSeekBar: SeekBar
    lateinit var song: TextView
    lateinit var imageView: ImageView
    lateinit var button: ImageButton

    private lateinit var nativeAdContainer: RelativeLayout
    private lateinit var bannerAdFrameLayout: FrameLayout
    val nativeandBanner = NativeAndBannerAds()
    var interStitialAds = InterStitialAds()
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

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


        //Getting required data from Intent bundle
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

    }
}