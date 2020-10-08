package com.boristul.notebook.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boristul.notebook.ui.MainActivity
import com.boristul.utils.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity>()
        finish()
    }
}
