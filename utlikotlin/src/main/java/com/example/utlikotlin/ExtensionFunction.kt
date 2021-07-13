package com.example.utlikotlin

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.io.OutputStream
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun Int.dp(context: Context) = this * context.resources.displayMetrics.density

fun Long.toDateTimeString(dateTimeFormat: String): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)
    val localDateTime = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)

    return localDateTime.format(dateTimeFormatter)
}

fun Double.roundDecimal(digit: Int) = "%,.${digit}f".format(this)

fun Float.roundDecimal(digit: Int) = "%,.${digit}f".format(this)

fun String.toBytes() = this.toByteArray(Charset.forName("GBK"))

fun String.isIpAddress() = Patterns.IP_ADDRESS.matcher(this).matches()

fun String.toDateTimeLong(dateTimeFormat: String): Long {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)

    return LocalDateTime.parse(this, dateTimeFormatter).toEpochSecond(ZoneOffset.UTC)
}

fun Bitmap.toEscBytes() = EscBitmapHelper.getBytes(this)

fun OutputStream.write(text: String) = this.write(text.toBytes())

fun OutputStream.writeln(text: String) = this.write("$text\n".toBytes())

fun OutputStream.write(bitmap: Bitmap) = this.write(bitmap.toEscBytes())

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

fun <T> List<T>.range(fromIndex: Int, toIndex: Int) = this.subList(fromIndex, toIndex + 1)

fun View.indexOfParent() = (this.parent as ViewGroup).indexOfChild(this)

fun View.scale(value: Float, duration: Long) {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, value)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, value)

    val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)

    animator.duration = duration

    animator.start()
}

fun View.isTouched(motionEvent: MotionEvent): Boolean {
    val rect = Rect()

    this.getGlobalVisibleRect(rect)

    return rect.contains(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
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

fun CardView.mapColor(arrayResourceId: Int, colorIndex: Int) {
    val colors = resources.obtainTypedArray(arrayResourceId)
    val color = this.context.getColor(colors.getResourceId(colorIndex, 0))

    this.setCardBackgroundColor(color)

    colors.recycle()
}

fun PopupWindow.build(contentView: View) = this.apply {
    setContentView(contentView)
    setBackgroundDrawable(null)

    isOutsideTouchable = true
    isFocusable = true
}

fun PopupWindow.showAsAbove(anchorView: View) = this.showAsDropDown(anchorView, 0, -anchorView.height * 4)

fun ImageButton.setEnableWithEffect(isEnable: Boolean) {
    if (isEnable) {
        isEnabled = true
        imageAlpha = 0xFF
    } else {
        isEnabled = false
        imageAlpha = 0x3F
    }
}

fun TextInputEditText.focusOnLast() {
    requestFocus()

    setSelection(text.toString().length)
}

fun Context.getConnectivityManager() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.cancelNotification() = getSystemService(NotificationManager::class.java).cancelAll()

fun Context.closeNotificationPanel() {
    val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)

    sendBroadcast(intent)
}

fun AppCompatActivity.getNavHostFragmentById(id: Int) = supportFragmentManager.findFragmentById(id) as NavHostFragment

fun LifecycleOwner.isConfigChanging() = (this as Fragment).requireActivity().isChangingConfigurations

fun Intent.isResolvable(context: Context) = resolveActivity(context.packageManager) != null

fun Fragment.getConnectivityManager() = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Fragment.getNotificationManager() = requireContext().getSystemService(NotificationManager::class.java) as NotificationManager

fun Fragment.getFragmentById(id: Int) = childFragmentManager.findFragmentById(id)

fun Fragment.getMapFragmentById(id: Int) = childFragmentManager.findFragmentById(id) as SupportMapFragment

fun Fragment.showToast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

fun Fragment.showToast(resourceId: Int) = Toast.makeText(requireContext(), resourceId, Toast.LENGTH_SHORT).show()

fun Fragment.showToastLong(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun Fragment.showToastLong(resourceId: Int) = Toast.makeText(requireContext(), resourceId, Toast.LENGTH_LONG).show()

fun Fragment.showSnackbar(text: String) {
    val view = requireActivity().findViewById<View>(android.R.id.content)

    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.pickPhoto(request: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }

    request.launch(intent)
}

fun Fragment.pickAndSavePhoto(requestCode: Int) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

    intent.type = "image/*"

    startActivityForResult(intent, requestCode)
}

fun Fragment.takeAndSavePicture(requestCode: Int, imageUri: Uri) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

    startActivityForResult(intent, requestCode)
}

fun Fragment.requestGPSOn(requestCode: Int) {
    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_LOW_POWER
    }

    val settingRequest = LocationSettingsRequest.Builder().run {
        addLocationRequest(locationRequest)
        build()
    }

    val settingsClient = LocationServices.getSettingsClient(requireContext())
    val checkTask = settingsClient.checkLocationSettings(settingRequest)

    checkTask.addOnFailureListener {
        val intentSender = (it as ResolvableApiException).resolution.intentSender

        startIntentSenderForResult(intentSender, requestCode, null, 0, 0, 0, null)
    }
}

fun Fragment.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.openApp(packageName: String) {
    val intent = requireContext().packageManager.getLaunchIntentForPackage(packageName)

    startActivity(intent)
}

fun Fragment.getImageUri(fileName: String, folderName: String): Uri {
    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$folderName")
    }

    return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
}

fun Fragment.deleteImage(imageUri: Uri) = requireContext().contentResolver.delete(imageUri, null, null)

fun Fragment.getRawUri(resourceId: Int) = "android.resource://${requireContext().packageName}/$resourceId".toUri()

fun Fragment.getRawUris(arrayResourceId: Int): List<Uri> {
    val rawUris = mutableListOf<Uri>()
    val raws = requireContext().resources.obtainTypedArray(arrayResourceId)

    for (index in 0 until raws.length()) {
        val resourceId = raws.getResourceId(index, 0)

        rawUris.add(getRawUri(resourceId))
    }

    raws.recycle()

    return rawUris
}

fun Fragment.isLandscapeMode() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Fragment.rotateScreen() {
    if (isLandscapeMode()) {
        setPortraitMode()
    } else {
        setLandscapeMode()
    }
}

fun Fragment.setLandscapeMode() {
    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

fun Fragment.setPortraitMode() {
    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Fragment.setReverseLandscapeMode() {
    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
}

fun Fragment.setReversePortraitMode() {
    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
}

fun Fragment.setFullScreenMode(isEnable: Boolean) {
    if (isEnable) {
        val systemUiFlag = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        requireActivity().window.decorView.systemUiVisibility = systemUiFlag

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    } else {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}

fun Fragment.setFragmentResult(requestKeyResId: Int, result: Bundle) {
    parentFragmentManager.setFragmentResult(getString(requestKeyResId), result)
}

fun Fragment.setFragmentResultListener(requestKeyResId: Int, listener: (String, Bundle) -> Unit) {
    parentFragmentManager.setFragmentResultListener(getString(requestKeyResId), this, listener)
}

fun Fragment.setActionBar(toolbar: MaterialToolbar) {
    (requireActivity() as AppCompatActivity).apply {
        supportActionBar?.hide()

        setSupportActionBar(toolbar)
    }
}

fun AndroidViewModel.getConnectivityManager(): ConnectivityManager {
    return (getApplication() as Context).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}

fun AndroidViewModel.showToast(text: String) = Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show()

fun AndroidViewModel.showToast(resourceId: Int) = Toast.makeText(getApplication(), resourceId, Toast.LENGTH_SHORT).show()

fun AndroidViewModel.showToastLong(resourceId: Int) = Toast.makeText(getApplication(), resourceId, Toast.LENGTH_LONG).show()

fun AndroidViewModel.getRawUri(resourceId: Int) = "android.resource://${(getApplication() as Context).packageName}/$resourceId".toUri()

fun AndroidViewModel.getRawUris(arrayResourceId: Int): List<Uri> {
    val rawUris = mutableListOf<Uri>()
    val raws = (getApplication() as Context).resources.obtainTypedArray(arrayResourceId)

    for (index in 0 until raws.length()) {
        val resourceId = raws.getResourceId(index, 0)

        rawUris.add(getRawUri(resourceId))
    }

    raws.recycle()

    return rawUris
}

fun DownloadManager.getDownloadedFile(id: Long): DownloadedFile {
    var url = ""
    var status = DownloadStatus.RUNNING

    val query = DownloadManager.Query().setFilterById(id)
    val cursor = query(query)

    cursor.use {
        if (it.moveToFirst()) {
            url = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))

            val statusIndex = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

            status = when (statusIndex) {
                DownloadManager.STATUS_SUCCESSFUL -> DownloadStatus.SUCCESSFUL

                else -> DownloadStatus.FAILED
            }
        }
    }

    return DownloadedFile(url, status)
}