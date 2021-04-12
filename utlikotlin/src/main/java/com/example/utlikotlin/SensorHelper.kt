package com.example.utlikotlin

import android.content.Context
import android.provider.Settings

object SensorHelper {
    fun isAutoRotateOn(context: Context) = Settings.System.getInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 1
}