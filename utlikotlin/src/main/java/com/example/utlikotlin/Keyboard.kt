package com.example.utlikotlin

import android.app.Activity
import android.app.Service
import android.view.inputmethod.InputMethodManager

object Keyboard {
    fun hide(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager

        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}