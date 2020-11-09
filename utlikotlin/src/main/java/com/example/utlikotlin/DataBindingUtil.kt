package com.example.utlikotlin

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

object DataBindingUtil {
    @BindingAdapter("android:text", "isAddCase")
    @JvmStatic
    fun setText(editText: EditText, text: String, isAddCase: Boolean) {
        if (isAddCase) editText.setText("") else editText.setText(text)
    }

    @InverseBindingAdapter(attribute = "android:text")
    @JvmStatic
    fun getText(editText: EditText) = editText.text.toString().trim()
}