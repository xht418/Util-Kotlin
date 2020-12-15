package com.example.utlikotlin

import android.content.res.TypedArray
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

@BindingAdapter("android:text", "isAddCase")
fun setText(editText: EditText, text: String, isAddCase: Boolean) {
    if (isAddCase) editText.setText("") else editText.setText(text)
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(editText: EditText) = editText.text.toString().trim()

@BindingAdapter("imageIndex", "images")
fun setImageResource(imageView: ImageView, imageIndex: Int, images: TypedArray) {
    imageView.setImageResource(images.getResourceId(imageIndex, 0))
}

@BindingAdapter("wordIndex", "words")
fun setText(textView: TextView, wordIndex: Int, words: Array<String>) {
    textView.text = words[wordIndex]
}

@BindingAdapter("label", "wordIndex", "words")
fun setText(textView: TextView, label: String, wordIndex: Int, words: Array<String>) {
    val text = "$label: ${words[wordIndex]}"

    textView.text = text
}

@BindingAdapter("checkedIndex")
fun setChecked(toggleGroup: MaterialButtonToggleGroup, checkedIndex: Int) {
    (toggleGroup.getChildAt(checkedIndex) as MaterialButton).isChecked = true
}

@BindingAdapter("checkedIndexes")
fun setChecked(toggleGroup: MaterialButtonToggleGroup, checkedIndexes: List<Int>) {
    checkedIndexes.forEach {
        (toggleGroup.getChildAt(it) as MaterialButton).isChecked = true
    }
}