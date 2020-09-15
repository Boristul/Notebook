package com.boristul.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.findNavControllerInOnCreate(@IdRes viewId: Int): NavController =
    (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController