package com.boristul.utils

import android.view.Menu
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.forEach

fun Menu.setColor(@ColorInt color: Int) = forEach { item ->
    item.icon?.let { DrawableCompat.setTint(it, color) }
}
