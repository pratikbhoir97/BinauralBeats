package com.example.binauralbeats

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.ump.*

class MyApplication : Application() {

companion object{

    var bannerAdId :String= "ca-app-pub-3940256099942544/6300978111"
    var nativeAdId :String= "ca-app-pub-3940256099942544/2247696110"
    var interstitailAdId : String="ca-app-pub-3940256099942544/1033173712"
    var exitInterstitailAdId :String= "ca-app-pub-3940256099942544/1033173712"

}
    override fun onCreate() {

        if(BuildConfig.DEBUG){
            bannerAdId =  "ca-app-pub-3940256099942544/6300978111"
            nativeAdId =  "ca-app-pub-3940256099942544/2247696110"
            interstitailAdId =  "ca-app-pub-3940256099942544/1033173712"
            exitInterstitailAdId =  "ca-app-pub-3940256099942544/1033173712"
        }else{

        }
        super.onCreate()
    }


}



