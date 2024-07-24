package com.example.movieapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.R

@BindingAdapter(
    "path"
)
fun loadImage(iv: ImageView, url: String) {
    Glide.with(iv.context)
        .load(url)
        .placeholder(R.drawable.placeholder_image)
        .into(iv)
}