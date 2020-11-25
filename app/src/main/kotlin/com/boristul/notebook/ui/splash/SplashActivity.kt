package com.boristul.notebook.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boristul.notebook.ui.MainActivity
import com.boristul.utils.startActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.crashlytics.log("OnCreate")
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "SplashActivity")
        }

        startActivity<MainActivity>()
        finish()
    }
}
