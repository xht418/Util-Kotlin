package com.example.utlikotlin

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object Keyboard {
    fun show(view: View) = ViewCompat.getWindowInsetsController(view)?.show(WindowInsetsCompat.Type.ime())
}