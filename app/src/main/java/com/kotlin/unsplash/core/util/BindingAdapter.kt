package com.kotlin.unsplash.core.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageNetwork")
fun loadImageNetwork(imageView: ImageView, url: String?){
    try {
        Picasso.get().load(url).into(imageView)
    }catch (e: Exception){
        Log.e("Exception", "$e")
    }
}
