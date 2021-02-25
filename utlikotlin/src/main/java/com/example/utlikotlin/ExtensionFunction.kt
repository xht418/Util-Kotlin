package com.example.utlikotlin

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import java.io.OutputStream
import java.lang.StringBuilder
import java.nio.charset.Charset

fun Double.roundDecimal(digit: Int) = "%,.${digit}f".format(this)

fun Float.roundDecimal(digit: Int) = "%,.${digit}f".format(this)

fun <T> List<T>.range(fromIndex: Int, toIndex: Int) = this.subList(fromIndex, toIndex + 1)

fun Int.dp(context: Context) = this * context.resources.displayMetrics.density

fun View.indexOfParent() = (this.parent as ViewGroup).indexOfChild(this)

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

fun Uri.toBitmap(context: Context): Bitmap? {
    val bitmap: Bitmap

    return try {
        val inputStream = context.contentResolver.openInputStream(this)

        inputStream.use {
            bitmap = BitmapFactory.decodeStream(it)
        }

        bitmap
    } catch (e: Exception) {
        Log.e("Uri.toBitmap()", "Image not found.")

        null
    }
}

fun ViewGroup.getCheckedIndexes(): List<Int> {
    val checkedIndexes = mutableListOf<Int>()

    this.forEachIndexed { index, view ->
        if ((view as CompoundButton).isChecked) {
            checkedIndexes.add(index)
        }
    }

    return checkedIndexes
}

fun View.isTouched(motionEvent: MotionEvent): Boolean {
    val rect = Rect()

    this.getGlobalVisibleRect(rect)

    return rect.contains(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
}

fun PopupWindow.showAsAbove(anchorView: View) = this.showAsDropDown(anchorView, 0, -anchorView.height * 4)