package com.kotlin.unsplash.ui

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.database.PhotoDatabase
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
        val dataSource = PhotoDatabase.getInstance(application).photoDao

        val viewModel = ViewModelProvider(this, PhotoDetailViewModelFactory(dataSource, photoArgs.photo, unsplashService, application))
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
                if (it != null) {
                    setWallpaper(application)
                }
            })
        }

        binding.explorePhotoButton.setOnClickListener {
            viewModel.photo.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    explorePhoto(it.id)
                }
            })
        }

        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(application, "Added favorites", Toast.LENGTH_SHORT).show()
                viewModel.showToastComplete()
            }
        })

        (activity as MainActivity).supportActionBar?.title = ""
        setHasOptionsMenu(true)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.actionbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorites_button -> binding.viewModel?.onFavoritesClicked()
        }
        return super.onOptionsItemSelected(item)
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