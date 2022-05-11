package com.kotlin.unsplash.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.kotlin.unsplash.R
import com.kotlin.unsplash.databinding.FragmentPhotoDetailBinding
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.viewmodel.PhotoDetailViewModel
import com.kotlin.unsplash.viewmodel.PhotoDetailViewModelFactory

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

        val viewModel = ViewModelProvider(this, PhotoDetailViewModelFactory(photoArgs.photo, unsplashService, application))
            .get(PhotoDetailViewModel::class.java)


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}