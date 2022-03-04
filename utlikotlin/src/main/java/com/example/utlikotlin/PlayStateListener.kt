package com.example.utlikotlin

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

class PlayStateListener(private val playEndedAction: () -> Unit) : Player.Listener {
    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)

        if (state == ExoPlayer.STATE_ENDED) {
            playEndedAction()
        }
    }
}