package com.example.homegarden.util

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, error: String?) {
    view.error = error
}

@BindingAdapter("app:setBitmap")
fun setImageBitmap(view: ImageView, bitmap: Bitmap?) {
    if (bitmap != null)
        view.setImageBitmap(bitmap)
}

@BindingAdapter("app:image")
fun setImage(view: ImageView, url: String?){
    if(url!=null){
        Glide.with(view.context).load(url).into(view)
    }
}