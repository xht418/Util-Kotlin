package com.example.utlikotlin

import android.content.Context
import android.view.OrientationEventListener

class OrientationListener(
    private val context: Context,
    private val degree0Action: () -> Unit,
    private val degree90Action: () -> Unit,
    private val degree180Action: () -> Unit,
    private val degree270Action: () -> Unit,
    var isFullScreenModeToggled: Boolean = false
) : OrientationEventListener(context) {
    companion object {
        const val TOLERANCE = 20
    }

    private var isDisablePortrait = false
    private var isDisableLandscape = false

    private fun reset() {
        isFullScreenModeToggled = false
        isDisablePortrait = false
        isDisableLandscape = false
    }

    override fun onOrientationChanged(degree: Int) {
        if (SensorHelper.isAutoRotateOn(context)) {
            if (isFullScreenModeToggled) {
                if (Device.isLandscapeMode(context)) {
                    isDisablePortrait = true
                    isDisableLandscape = false
                } else {
                    isDisableLandscape = true
                    isDisablePortrait = false
                }
            }

            when (degree) {
                in (0..0 + TOLERANCE) -> {
                    if (!isDisablePortrait) {
                        degree0Action()

                        reset()
                    }
                }

                in (359 - TOLERANCE..359) -> {
                    if (!isDisablePortrait) {
                        degree0Action()

                        reset()
                    }
                }

                in (90 - TOLERANCE..90 + TOLERANCE) -> {
                    if (!isDisableLandscape) {
                        degree90Action()

                        reset()
                    }
                }

                in (180 - TOLERANCE..180 + TOLERANCE) -> {
                    if (!isDisablePortrait) {
                        degree180Action()

                        reset()
                    }
                }

                in (270 - TOLERANCE..270 + TOLERANCE) -> {
                    if (!isDisableLandscape) {
                        degree270Action()

                        reset()
                    }
                }
            }
        }
    }
}