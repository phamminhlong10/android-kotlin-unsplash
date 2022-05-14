package com.kotlin.unsplash.ui

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.databinding.FragmentPhotoDetailBinding
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.viewmodel.PhotoDetailViewModel
import com.kotlin.unsplash.viewmodel.PhotoDetailViewModelFactory
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_detail, container, false)

        val photoArgs by navArgs<PhotoDetailFragmentArgs>()
        val unsplashService = UnsplashApi.retrofitService
        val application = requireNotNull(this.activity).application
        val context = this.requireActivity().applicationContext

        val viewModel = ViewModelProvider(this, PhotoDetailViewModelFactory(photoArgs.photo, unsplashService, application))
            .get(PhotoDetailViewModel::class.java)

        viewModel.photo.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                delay(500L)
                if(it != null){
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.circularProgressBar.visibility = View.GONE
                    binding.textError.visibility = View.VISIBLE
                }
            }
        })

        binding.setWallpaperButton.setOnClickListener {
            viewModel.photo.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    if (it != null) {
                        setWallpaper(it.urls.regular, context)
                    }
                }
            })
        }
        (activity as MainActivity).supportActionBar?.title = ""
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setWallpaper(url: String?, context: Context){
        Picasso.get().load(url).into(object: Target{
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("TAG", "FAILED");
            }
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("TAG", "Prepare Load");
            }
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(context);
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(context, "Set wallpaper successfully", Toast.LENGTH_LONG).show()
                }catch (e: IOException){
                    Log.e("IOException", e.toString())
                }
            }
        })
    }
}