package com.boristul.notebook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.boristul.notebook.databinding.ActivityMainBinding
import com.boristul.utils.findNavControllerInOnCreate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavControllerInOnCreate(binding.navHostFragment.id).apply {
            //addOnDestinationChangedListener { _, destination, _ -> title = destination.label }
        }
        NavigationUI.setupWithNavController(binding.navigation, navController)
    }
}
