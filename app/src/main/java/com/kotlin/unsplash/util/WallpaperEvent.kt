package com.kotlin.unsplash.util

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception

class WallpaperEvent {

    private fun setWallpaper(bitmap: Bitmap, context: Context){
        try{
            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(context, "Set wallpaper successfully", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    private fun setLockScreen(bitmap: Bitmap, context: Context){
        try{
            val wallpaperManager = WallpaperManager.getInstance(context)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                Toast.makeText(context, "Set wallpaper lockscreen", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Your system not supported for setting lock screen wallpaper ", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    private fun both(bitmap: Bitmap, context: Context){
        try{
            val wallpaperManager = WallpaperManager.getInstance(context)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                Toast.makeText(context, "Set both", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Your system not supported for setting lock screen wallpaper ", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    fun explorePhoto(id: String?, context: Context){
        val url = "https://unsplash.com/photos/${id}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(context, intent, null)
    }

    fun showAlertDialog(bitmap: Bitmap, context: Context, contextFragment: Context){
        val items = arrayOf("Set wallpaper", "Set lockscreen", "Both")
        contextFragment?.let {
            MaterialAlertDialogBuilder(it).setTitle("Set Wallpaper")
                .setItems(items){ dialog, which ->
                    when(which){
                        0 -> setWallpaper(bitmap, context)
                        1 -> setLockScreen(bitmap, context)
                        2 -> both(bitmap,context)
                    }
                }.show()
        }
    }
}
