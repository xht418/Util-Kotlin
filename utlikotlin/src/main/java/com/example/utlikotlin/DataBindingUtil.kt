package com.example.utlikotlin

import android.content.res.TypedArray
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

object DataBindingUtil {
    @BindingAdapter("android:text", "isAddCase")
    @JvmStatic
    fun setText(editText: EditText, text: String, isAddCase: Boolean) {
        if (isAddCase) editText.setText("") else editText.setText(text)
    }

    @InverseBindingAdapter(attribute = "android:text")
    @JvmStatic
    fun getText(editText: EditText) = editText.text.toString().trim()

    @BindingAdapter("imageIndex", "images")
    @JvmStatic
    fun setImageResource(imageView: ImageView, imageIndex: Int, images: TypedArray) {
        imageView.setImageResource(images.getResourceId(imageIndex, 0))
    }

    @BindingAdapter("wordIndex", "words")
    @JvmStatic
    fun setText(textView: TextView, wordIndex: Int, words: Array<String>) {
        textView.text = words[wordIndex]
    }

    @BindingAdapter("label", "wordIndex", "words")
    @JvmStatic
    fun setText(textView: TextView, label: String, wordIndex: Int, words: Array<String>) {
        val text = "$label: ${words[wordIndex]}"

        textView.text = text
    }

    @BindingAdapter("checkedIndex")
    @JvmStatic
    fun setChecked(toggleGroup: MaterialButtonToggleGroup, checkedIndex: Int) {
        (toggleGroup.getChildAt(checkedIndex) as MaterialButton).isChecked = true
    }

    @BindingAdapter("checkedIndexes")
    @JvmStatic
    fun setChecked(toggleGroup: MaterialButtonToggleGroup, checkedIndexes: List<Int>) {
        checkedIndexes.forEach {
            (toggleGroup.getChildAt(it) as MaterialButton).isChecked = true
        }
    }
}