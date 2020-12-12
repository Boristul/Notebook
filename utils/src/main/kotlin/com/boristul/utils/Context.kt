package com.boristul.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import org.joda.time.LocalDate
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

// region UI
fun Context.showDatePicker(
    preselectedDate: LocalDate? = null,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onDateSet: (selected: LocalDate) -> Unit
) {
    val preselected = preselectedDate ?: LocalDate.now()

    DatePickerDialog(
        this,
        { _, year, month, dayOfMonth -> onDateSet.invoke(LocalDate(year, month + 1, dayOfMonth)) },
        preselected.year,
        preselected.monthOfYear - 1,
        preselected.dayOfMonth
    ).apply {
        minDate?.let { datePicker.minDate = it.toDate().time }
        maxDate?.let { datePicker.maxDate = it.toDate().time }
    }.show()
}
// endregion
