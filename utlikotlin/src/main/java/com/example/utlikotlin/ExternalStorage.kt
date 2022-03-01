package com.example.utlikotlin

import androidx.core.net.toUri

object ExternalStorage {
    private const val ROOT_PATH = "content://com.android.externalstorage.documents/document/primary:"

    fun getPath(directory: String, folderName: String) = "$ROOT_PATH$directory%2$folderName".toUri()
}