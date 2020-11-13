package com.boristul.notebook.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boristul.notebook.ui.MainActivity
import com.boristul.utils.startActivity
import com.google.firebase.analytics.FirebaseAnalytics

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity>()
        FirebaseAnalytics.getInstance(this).apply {
            logEvent("splash_activity", null)
        }
        finish()
    }
}
