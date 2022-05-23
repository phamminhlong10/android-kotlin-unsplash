package com.kotlin.unsplash.util

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.lang.Exception

class WallpaperEvent {

    private fun onSetWallpaper(bitmap: Bitmap, context: Context){
        CoroutineScope(Dispatchers.Main).launch{
            setWallpaper(bitmap, context)
            Toast.makeText(context, "Set wallpaper successfully", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun setWallpaper(bitmap: Bitmap, context: Context){
        try{
            withContext(Dispatchers.Default){
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)
            }
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    private suspend fun setLockScreen(bitmap: Bitmap, context: Context){
        try{
            withContext(Dispatchers.Default){
                val wallpaperManager = WallpaperManager.getInstance(context)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
            }
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    private fun onSetLockScreen(bitmap: Bitmap, context: Context){
        CoroutineScope(Dispatchers.Main).launch {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                setLockScreen(bitmap, context)
                Toast.makeText(context, "Set wallpaper lockscreen", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Your system not supported for setting lock screen wallpaper ", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun setBoth(bitmap: Bitmap, context: Context){
        try{
            withContext(Dispatchers.Default){
                val wallpaperManager = WallpaperManager.getInstance(context)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                }
            }
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    private fun onSetBoth(bitmap: Bitmap, context: Context){
        CoroutineScope(Dispatchers.Main).launch {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                setBoth(bitmap, context)
                Toast.makeText(context, "Set lockscreen & wallpaper successful", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Your system not supported for setting lock screen wallpaper ", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun explorePhoto(id: String?, context: Context){
        val url = "https://unsplash.com/photos/${id}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(context, intent, null)
    }

    fun showAlertDialog(bitmap: Bitmap, context: Context, contextFragment: Context){
        val items = arrayOf("Set wallpaper", "Set lockscreen", "Both")
        contextFragment.let {
            MaterialAlertDialogBuilder(it).setTitle("Set Wallpaper")
                .setItems(items){ _, which ->
                    when(which){
                        0 -> onSetWallpaper(bitmap, context)
                        1 -> onSetLockScreen(bitmap, context)
                        2 -> onSetBoth(bitmap,context)
                    }
                }.show()
        }
    }
}
