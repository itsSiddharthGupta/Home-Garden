package com.example.homegarden.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, error: String?) {
    view.error = error
}