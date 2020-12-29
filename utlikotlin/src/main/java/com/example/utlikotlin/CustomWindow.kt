package com.example.utlikotlin

import android.content.Context
import android.view.View
import android.widget.PopupWindow

object CustomWindow {
    fun build(context: Context, contentView: View): PopupWindow {
        val popupWindow = PopupWindow(context)

        popupWindow.contentView = contentView
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(null)

        return popupWindow
    }
}