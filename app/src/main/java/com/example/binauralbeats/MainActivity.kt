package com.example.binauralbeats

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binauralbeats.AdMobAds.InterStitialAds
import com.example.binauralbeats.AdMobAds.NativeAndBannerAds
import com.example.binauralbeats.utils.CommonData
import com.example.binauralbeats.utils.EUconsent
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.*
import com.google.android.ump.*

class MainActivity : AppCompatActivity() {
    lateinit var consentInformation: ConsentInformation
    private var consentForm: ConsentForm? = null

    lateinit var recyclerView1: RecyclerView
    lateinit var adapter: MusicAdapter
    lateinit var adView: AdView


    //Native and Banner Ads
    private lateinit var nativeAdContainer: RelativeLayout
    private lateinit var bannerAdFrameLayout: FrameLayout

   val nativeandBanner = NativeAndBannerAds()
    var interStitialAds = InterStitialAds()

    lateinit var activity: Activity


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        interStitialAds.showInterstitalAd(activity)
        super.onBackPressed()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

activity=this
        recyclerView1 = findViewById(R.id.Recyclerview)
        recyclerView1.layoutManager = GridLayoutManager(this, 2)
        adapter =
            MusicAdapter(CommonData.imageList, CommonData.nameList, CommonData.audioBeats, this)
        recyclerView1.adapter = adapter

  //    getConsentStatus()


        MobileAds.initialize(this) {}
        // Find the native and banner ad view container in the layout
        nativeAdContainer = findViewById(R.id.native_ad_container)
        bannerAdFrameLayout = findViewById(R.id.banner_ad_container)
        nativeandBanner.loadNativeOrBannerAds(nativeAdContainer,bannerAdFrameLayout,this)

        interStitialAds.loadInterStitialAds(this)



    }


 /* fun getConsentStatus() {
        consentInformation = UserMessagingPlatform.getConsentInformation(this)

        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId("TEST-DEVICE-HASHED-ID")
            .build()
        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .build()
        consentInformation.requestConsentInfoUpdate(this, params,
            {
                if (consentInformation.isConsentFormAvailable()) {
                    loadForm()
                }else{

                }

            },
            {
                Toast.makeText(applicationContext, "Form load again", Toast.LENGTH_LONG).show()
                loadForm()
            })
    }

    fun loadForm() {
        UserMessagingPlatform.loadConsentForm(
            this,
            { consentForm ->
                this@MainActivity.consentForm = consentForm
                if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    consentForm.show(
                        this@MainActivity
                    ) { // Handle dismissal by reloading form.
                        loadForm()
                    }
                }
            }
        ) {
            Toast.makeText(applicationContext, "Form error", Toast.LENGTH_LONG).show()
        }
    }*/
}
