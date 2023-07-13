package com.binauralbeats.brainwave.meditation.relaxation.music.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle


import android.widget.Toast
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.ump.*



class EUconsent(context: Context) {

        fun getPersonalizedAdsBundle(): Bundle {
            val extras = Bundle()
            extras.putString("npa", "0")
            return extras
        }

        fun getNonPersonalizedAdsBundle(): Bundle {
            val extras = Bundle()
            extras.putString("npa", "1")
            return extras
        }

        fun showEUConsentDialog(context: Context, listener: (Bundle?) -> Unit) {
            val preferences =
                context.getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE)
            val isPersonalizedAdsEnabled = preferences.getBoolean("personalized_ads", true)

            if (isPersonalizedAdsEnabled) {
                // Show personalized ads
                listener.invoke(getPersonalizedAdsBundle())
            } else {
                // Show non-personalized ads
                val builder = AlertDialog.Builder(context)
                builder.setTitle("EU Consent")
                    .setMessage("Do you consent to personalized ads?")
                    .setPositiveButton("Accept") { _, _ ->
                        preferences.edit().putBoolean("personalized_ads", true).apply()
                        listener.invoke(getPersonalizedAdsBundle())
                    }
                    .setMessage("Do you consent to NonPersonalized ads?")
                    .setNegativeButton("Reject") { _, _ ->
                        preferences.edit().putBoolean("personalized_ads", false).apply()
                        listener.invoke(getNonPersonalizedAdsBundle())
                    }
                builder.create().show()
            }
        }

    }















