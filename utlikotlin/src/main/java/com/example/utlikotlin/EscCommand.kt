package com.example.utlikotlin

object EscCommand {
    const val LENGTH = 48

    val ALIGN_CENTER = byteArrayOf(0x1b, 0x61, 0x01)
    val ALIGN_LEFT = byteArrayOf(0x1b, 0x61, 0x00)
    val ALIGN_RIGHT = byteArrayOf(0x1b, 0x61, 0x02)

    val TEXT_SIZE_NORMAL = byteArrayOf(0x1b, 0x21, 0x00)
    val TEXT_SIZE_LARGE = byteArrayOf(0x1b, 0x21, 0x30)

    val TEXT_BOLD_ON = byteArrayOf(0x1b, 0x45, 0x01)
    val TEXT_BOLD_OFF = byteArrayOf(0x1b, 0x45, 0x00)
    val INVERT_COLOR_ON = byteArrayOf(0x1d, 0x42, 0x01)
    val INVERT_COLOR_OFF = byteArrayOf(0x1d, 0x42, 0x00)

    val IMAGE_MODE = byteArrayOf(0x1B, 0x2A, 33)

    val CUT_PAPER = byteArrayOf(0x1d, 0x56, 0x41, 0x10)
    val OPEN_CASH_DRAWER = byteArrayOf(0x1b, 0x70, 0x00)
}