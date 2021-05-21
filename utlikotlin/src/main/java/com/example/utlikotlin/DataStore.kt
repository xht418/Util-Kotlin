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
}