package com.example.utlikotlin

import android.content.Context
import android.content.res.Configuration

object Device {
    fun isLandscapeMode(context: Context) = context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}