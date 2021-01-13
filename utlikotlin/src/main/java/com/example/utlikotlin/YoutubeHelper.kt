package com.example.utlikotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

object YoutubeHelper {
    fun playVideo(context: Context, videoUrl: String) {
        var intent = Intent(Intent.ACTION_VIEW, videoUrl.toAppVideoUri())

        if (intent.resolveActivity(context.packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, videoUrl.toUri())
        }

        context.startActivity(intent)
    }

    fun openChannel(context: Context, channelUrl: String) {
        var intent = Intent(Intent.ACTION_VIEW, channelUrl.toAppChannelUri())

        if (intent.resolveActivity(context.packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, channelUrl.toUri())
        }

        context.startActivity(intent)
    }

    private fun String.toAppVideoUri(): Uri {
        val videoId = this.toUri().getQueryParameter("v")

        return "vnd.youtube:$videoId".toUri()
    }

    private fun String.toAppChannelUri(): Uri {
        val channelId = this.toUri().lastPathSegment

        return "vnd.youtube://channel/$channelId".toUri()
    }
}