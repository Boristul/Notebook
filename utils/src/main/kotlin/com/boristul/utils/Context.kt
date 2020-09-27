package com.boristul.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

// region Compat methods

fun Context.getColorCompat(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawableCompat(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

// endregion

// region Android size converters

fun Context.dpToPx(dp: Int) =
    (dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

fun Context.pxToDp(px: Int) =
    (px / (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()

fun Context.spToPx(sp: Float) =
    (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics))

fun Context.pxToSp(px: Int) = (px / resources.displayMetrics.scaledDensity).roundToInt()

// endregion
