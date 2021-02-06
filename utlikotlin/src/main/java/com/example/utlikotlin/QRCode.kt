package com.example.utlikotlin

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

object QRCode {
    fun generate(text: String, width: Int, height: Int): Bitmap {
        val encodedText = String(text.toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)

        return BarcodeEncoder().encodeBitmap(encodedText, BarcodeFormat.QR_CODE, width, height)
    }
}