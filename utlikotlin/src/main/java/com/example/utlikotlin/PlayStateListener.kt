package com.example.utlikotlin

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayStateListener(private val playEndedAction: () -> Unit) : Player.EventListener {
    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)

        if (state == SimpleExoPlayer.STATE_ENDED) {
            playEndedAction()
        }
    }
}