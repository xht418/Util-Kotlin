package com.example.utlikotlin

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import java.io.OutputStream
import java.lang.StringBuilder
import java.nio.charset.Charset

fun Double.roundDecimal(digit: Int) = "%,.${digit}f".format(this)

fun Float.roundDecimal(digit: Int) = "%,.${digit}f".format(this)

fun <T> List<T>.range(fromIndex: Int, toIndex: Int) = this.subList(fromIndex, toIndex + 1)

fun Int.dp(context: Context) = this * context.resources.displayMetrics.density

fun View.indexOfParent() = (this.parent as LinearLayout).indexOfChild(this)

fun View.scale(value: Float, duration: Long) {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, value)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, value)

    val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)

    animator.duration = duration

    animator.start()
}

fun CardView.mapColor(arrayResourceId: Int, colorIndex: Int) {
    val colors = resources.obtainTypedArray(arrayResourceId)
    val color = this.context.getColor(colors.getResourceId(colorIndex, 0))

    this.setCardBackgroundColor(color)

    colors.recycle()
}

fun PopupWindow.build(contentView: View): PopupWindow {
    this.contentView = contentView
    this.isOutsideTouchable = true
    this.setBackgroundDrawable(null)

    return this
}

fun AppCompatActivity.getNavHostFragmentById(id: Int) = supportFragmentManager.findFragmentById(id) as NavHostFragment

fun String.toBytes() = this.toByteArray(Charset.forName("GBK"))

fun OutputStream.write(text: String) = this.write(text.toBytes())

fun OutputStream.writeln(text: String) = this.write("$text\n".toBytes())

fun Bitmap.toEscBytes() = EscBitmapHelper.getBytes(this)

fun OutputStream.write(bitmap: Bitmap) = this.write(bitmap.toEscBytes())

fun Fragment.pickPhoto(requestCode: Int) {
    val intent = Intent(Intent.ACTION_PICK)

    intent.type = "image/*"

    startActivityForResult(intent, requestCode)
}

fun Fragment.pickAndSavePhoto(requestCode: Int) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

    intent.type = "image/*"

    startActivityForResult(intent, requestCode)
}