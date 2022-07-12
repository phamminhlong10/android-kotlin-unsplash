package com.kotlin.unsplash.features.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.databinding.FragmentPhotoDBDetailBinding
import com.kotlin.unsplash.core.util.WallpaperEvent
import com.kotlin.unsplash.features.presentation.viewmodel.PhotoDBDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDBDetailFragment : Fragment() {
    private lateinit var binding: FragmentPhotoDBDetailBinding
    private val viewModel: PhotoDBDetailViewModel by viewModels()
    @Inject lateinit var wallpaperEvent: WallpaperEvent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_d_b_detail, container, false)
        val application = requireNotNull(this.activity).application

        binding.setWallpaperPhotoDB.setOnClickListener {
            viewModel.photoDB.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    wallpaperEvent.showAlertDialog(binding.photoView.drawable.toBitmap(), application,
                        requireNotNull(this@PhotoDBDetailFragment.context))
                }
            })
        }

        binding.explorePhotoDBButton.setOnClickListener {
            viewModel.photoDB.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    wallpaperEvent.explorePhoto(it.id, requireNotNull(this@PhotoDBDetailFragment.context))
                }
            })
        }


        (activity as MainActivity).supportActionBar?.title = ""
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}