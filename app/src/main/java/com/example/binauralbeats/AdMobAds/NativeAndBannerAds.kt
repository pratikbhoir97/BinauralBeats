package com.example.binauralbeats.AdMobAds
/*
* TODO : Initialize below 3 variables in Activity class
* //Native and Banner Ads
*    private lateinit var nativeAdContainer: RelativeLayout
*    private lateinit var bannerAdFrameLayout: FrameLayout
*    val nativeandBanner = NativeAndBannerAds()
*
* TODO : Add below code in onCreate method
*  
        MobileAds.initialize(this) {}
        // Find the native and banner ad view container in the layout
        nativeAdContainer = findViewById(R.id.native_ad_container)
        bannerAdFrameLayout = findViewById(R.id.banner_ad_container)
        nativeandBanner.loadNativeOrBannerAds(nativeAdContainer,bannerAdFrameLayout,this)

* TODO:  XML code for banner and Native ads in Activity
*
        <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_alignParentBottom="true">

        <FrameLayout
            android:id="@+id/banner_ad_container"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:visibility="gone"
            />

        <RelativeLayout
            android:id="@+id/native_ad_container"
            android:layout_width="match_parent"
            android:layout_height="80dp"
            android:orientation="vertical"
            android:background="#03A9F4"

            />
    </LinearLayout>
    *
    * TODO: create native_ad_layout.xml with following code in layout folder
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:id="@+id/native_ad_container_sample"
android:layout_width="match_parent"
android:layout_height="wrap_content">

<ImageView
    android:id="@+id/native_ad_image"
    android:layout_width="80dp"
    android:layout_height="80dp"
    android:adjustViewBounds="true"
    android:scaleType="centerCrop"
    android:layout_marginStart="5dp"
    android:layout_alignParentStart="true"/>

<TextView
    android:id="@+id/native_ad_headline"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginLeft="5dp"
    android:layout_toRightOf="@id/native_ad_image"
    android:textAppearance="@android:style/TextAppearance.Medium" />

<Button
    android:id="@+id/native_ad_cta"
    android:layout_width="match_parent"
    android:layout_height="35dp"
    android:background="#FFEB3B"
    android:layout_margin="5dp"
    android:layout_toRightOf="@id/native_ad_image"
    android:layout_alignParentBottom="true"/>

</RelativeLayout>
*
* */
import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.binauralbeats.MyApplication
import com.example.binauralbeats.R
import com.example.binauralbeats.utils.EUconsent
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd

class NativeAndBannerAds {
    //Banner Ads
    private lateinit var adView: AdView
    private lateinit var bannerAdContainerView: FrameLayout
    private var initialLayoutComplete = false
    lateinit var activity: Activity

    fun loadNativeOrBannerAds(nativeAdContainer : ViewGroup, bannerAdFrameLayout: FrameLayout, activity: Activity){
        // Load the native ad
        val adLoader = AdLoader.Builder(activity, MyApplication.nativeAdId)
            .forNativeAd { ad: NativeAd ->
                // Populate the native ad views
                val nativeAdView =
                    activity.layoutInflater.inflate(R.layout.native_ad_layout, null) as RelativeLayout
                populateNativeAdView(ad, nativeAdView)
                nativeAdContainer.addView(nativeAdView)
            }
            .withAdListener(object : AdListener() {
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle ad failed to load event
                    nativeAdContainer.visibility = View.GONE
                    bannerAdFrameLayout.visibility = View.VISIBLE
                    loadBanner(activity,bannerAdFrameLayout)
                }
            })
            .build()
        val euconsent= EUconsent(activity)
        euconsent.showEUConsentDialog(activity)  { adRequestConfig ->
            val adRequest = AdRequest.Builder().apply {
                adRequestConfig?.let { addNetworkExtrasBundle(AdMobAdapter::class.java, it) }
            }.build()
            adLoader.loadAd(adRequest)
        }
//        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: RelativeLayout) {
        // Populate the native ad views
        adView.findViewById<ImageView>(R.id.native_ad_image)
            .setImageDrawable(nativeAd.images.first().drawable)
        adView.findViewById<TextView>(R.id.native_ad_headline).text = "${nativeAd.headline}"
        adView.findViewById<Button>(R.id.native_ad_cta).text = nativeAd.callToAction
//        adView.findViewById<RatingBar>(R.id.native_ad_rating).rating =
//            nativeAd.starRating?.toFloat()!!
    }
    /////////Banner Ads
    // Determine the screen width (less decorations) to use for the ad width.

    private fun getAdSize(activity:Activity):AdSize {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             val windowMetrics = activity.windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds

            var adWidthPixels =bannerAdContainerView.getWidth().toFloat()

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width().toFloat()
            }

            val density = activity.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        } else {
            val display = activity.windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = bannerAdContainerView.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        }



    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun loadBanner(activity: Activity, frameLayout: FrameLayout) {
        adView = AdView(activity)
        bannerAdContainerView = frameLayout
        bannerAdContainerView.addView(adView)
        // Since we're loading the banner based on the adContainerView size, we need
        // to wait until this view is laid out before we can get the width.
        bannerAdContainerView.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                adView.adUnitId = AD_UNIT_ID

                adView.setAdSize(getAdSize(activity))

                // Create an ad request. Check your logcat output for the hashed device ID to
                // get test ads on a physical device, e.g.,
                // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this device."
                val adRequest =  AdRequest.Builder().build()

                // Start loading the ad in the background.
//                adView.loadAd(adRequest)

                val euconsent= EUconsent(activity)
                euconsent.showEUConsentDialog(activity)  { adRequestConfig ->
                    val adRequest = AdRequest.Builder().apply {
                        adRequestConfig?.let { addNetworkExtrasBundle(AdMobAdapter::class.java, it) }
                    }.build()
                    adView.loadAd(adRequest)
                }
                adView.adListener = object: AdListener() {
                    override fun onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    override fun onAdClosed() {
                        // Code to be executed when the user is about to return
                        // to the app after tapping on an ad.
                    }

                    override fun onAdFailedToLoad(adError : LoadAdError) {
                        // Code to be executed when an ad request fails.
                        bannerAdContainerView.visibility = View.GONE
                    }

                    override fun onAdImpression() {
                        // Code to be executed when an impression is recorded
                        // for an ad.
                    }

                    override fun onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    override fun onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }
                }
            }
        }
    }
    fun destroyAdview(){
        if (adView!=null)
            adView.destroy()
    }
    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private val AD_UNIT_ID = MyApplication.bannerAdId
    }
}