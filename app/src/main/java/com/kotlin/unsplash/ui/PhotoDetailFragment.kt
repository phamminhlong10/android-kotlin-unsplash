package com.kotlin.unsplash.ui

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.database.PhotoDatabase
import com.kotlin.unsplash.databinding.FragmentPhotoDetailBinding
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.util.WallpaperEvent
import com.kotlin.unsplash.viewmodel.PhotoDetailViewModel
import com.kotlin.unsplash.viewmodel.PhotoDetailViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                    WallpaperEvent().showAlertDialog(binding.photoView.drawable.toBitmap(), application,
                        requireNotNull(this@PhotoDetailFragment.context))
                }
            })
        }

        binding.explorePhotoButton.setOnClickListener {
            viewModel.photo.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    WallpaperEvent().explorePhoto(it.id, requireNotNull(this@PhotoDetailFragment.context))
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
}