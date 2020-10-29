package com.boristul.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.findNavControllerInOnCreate(@IdRes viewId: Int): NavController =
    (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController

fun Fragment.hideKeyboard() {
    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view?.windowToken, 0)
}
