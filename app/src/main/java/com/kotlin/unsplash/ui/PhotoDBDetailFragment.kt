package com.kotlin.unsplash.ui

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.databinding.FragmentPhotoDBDetailBinding
import com.kotlin.unsplash.viewmodel.PhotoDBDetailViewModel
import com.kotlin.unsplash.viewmodel.PhotoDBDetailViewModelFactory
import java.lang.Exception


class PhotoDBDetailFragment : Fragment() {
    private lateinit var binding: FragmentPhotoDBDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_d_b_detail, container, false)
        val application = requireNotNull(this.activity).application
        val photoDBArgs by navArgs<PhotoDBDetailFragmentArgs>()

        val viewModel = ViewModelProvider(this, PhotoDBDetailViewModelFactory(photoDBArgs.photoDB, application)).get(PhotoDBDetailViewModel::class.java)



        binding.setWallpaperPhotoDB.setOnClickListener {
            viewModel.photoDB.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    setWallpaper(application)
                }
            })
        }

        binding.explorePhotoDBButton.setOnClickListener {
            viewModel.photoDB.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    explorePhoto(it.id)
                }
            })
        }


        (activity as MainActivity).supportActionBar?.title = ""
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    private fun setWallpaper(context: Context){
        try{
            val bitmap = binding.photoView.drawable.toBitmap()
            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(context, "Set wallpaper successfully", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e("Exception", e.toString())
        }
    }

    private fun explorePhoto(id: String?){
        val url = "https://unsplash.com/photos/${id}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}