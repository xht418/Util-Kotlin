package com.example.utlikotlin

import android.net.Uri

object ExternalStorage {
    private val rootPath = "content://com.android.externalstorage.documents/document/primary:"

    fun getPath(directory: String, folderName: String) = Uri.parse("$rootPath$directory%2$folderName")
}