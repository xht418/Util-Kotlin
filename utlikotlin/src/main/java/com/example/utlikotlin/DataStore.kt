package com.example.utlikotlin

import android.content.Context
import android.net.Uri
import androidx.core.content.edit
import androidx.core.net.toUri

object DataStore {
    fun saveImageUri(context: Context, imageName: String, imageUri: Uri) {
        val sharedPreferences = context.getSharedPreferences("images", Context.MODE_PRIVATE)

        sharedPreferences.edit {
            putString(imageName, imageUri.toString())
        }
    }

    fun getImageUri(context: Context, imageName: String): Uri? {
        val sharedPreferences = context.getSharedPreferences("images", Context.MODE_PRIVATE)
        val uriString = sharedPreferences.getString(imageName, "")

        return if (uriString!!.isEmpty()) null else uriString.toUri()
    }

    fun saveImageIndex(context: Context, imageName: String, imageIndex: Int) {
        val sharedPreferences = context.getSharedPreferences("images", Context.MODE_PRIVATE)

        sharedPreferences.edit {
            putInt(imageName, imageIndex)
        }
    }

    fun getImageIndex(context: Context, imageName: String): Int {
        val sharedPreferences = context.getSharedPreferences("images", Context.MODE_PRIVATE)

        return sharedPreferences.getInt(imageName, 0)
    }

    fun saveString(context: Context, keyResId: Int, value: String) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val key = context.getString(keyResId)

        sharedPreferences.edit {
            putString(key, value)
        }
    }

    fun getString(context: Context, keyResId: Int, defaultValueResId: Int = -1): String {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val key = context.getString(keyResId)
        val defaultValue = if (defaultValueResId == -1) "" else context.getString(defaultValueResId)

        return sharedPreferences.getString(key, defaultValue)!!
    }
}