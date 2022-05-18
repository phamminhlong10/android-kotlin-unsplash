package com.kotlin.unsplash.util

import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toBitmap
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
