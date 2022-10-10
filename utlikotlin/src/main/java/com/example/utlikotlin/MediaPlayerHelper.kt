package com.example.utlikotlin

import android.content.Context
import android.media.MediaPlayer

class MediaPlayerHelper(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playOrPauseAudio(file: Any, isOverlaid: Boolean, isPauseReset: Boolean) {
        if (mediaPlayer == null || isOverlaid) {
            mediaPlayer = getMediaPlayer(file, isOverlaid)

            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } else {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()

                if (isPauseReset) {
                    mediaPlayer!!.seekTo(0)
                }
            } else {
                mediaPlayer!!.start()
            }
        }
    }

    fun releasePlayer() {
        mediaPlayer?.release()

        mediaPlayer = null
    }

    private fun getMediaPlayer(file: Any, isOverlaid: Boolean) = MediaPlayer().apply {
        when (file) {
            is String -> context.assets.openFd(file).use {
                setDataSource(it.fileDescriptor, it.startOffset, it.length)
            }

            is Int -> context.resources.openRawResourceFd(file).use {
                setDataSource(it.fileDescriptor, it.startOffset, it.length)
            }

            else -> throw IllegalArgumentException()
        }

        setOnCompletionListener {
            if (isOverlaid) {
                release()
            }
        }
    }
}