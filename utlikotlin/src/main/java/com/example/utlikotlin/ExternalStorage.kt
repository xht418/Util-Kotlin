package com.example.utlikotlin

import androidx.core.net.toUri

object ExternalStorage {
    private const val rootPath = "content://com.android.externalstorage.documents/document/primary:"

    fun getPath(directory: String, folderName: String) = "$rootPath$directory%2$folderName".toUri()
}