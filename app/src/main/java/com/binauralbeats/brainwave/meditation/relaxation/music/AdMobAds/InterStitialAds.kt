package com.binauralbeats.brainwave.meditation.relaxation.music.AdMobAds

import android.app.Activity

import android.util.Log
import com.binauralbeats.brainwave.meditation.relaxation.music.MyApplication
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback



class InterStitialAds {
    lateinit var adRequest: AdRequest
    private var mInterstitialAd: InterstitialAd? = null


    fun loadInterStitialAds(activity: Activity) {
        MobileAds.initialize(activity) {}
        adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            MyApplication.interstitailAdId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    fun showInterstitalAd(activity: Activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }

    }
}
