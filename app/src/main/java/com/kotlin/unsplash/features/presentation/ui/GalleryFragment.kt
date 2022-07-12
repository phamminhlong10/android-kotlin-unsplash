package com.kotlin.unsplash.features.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.unsplash.R
import com.kotlin.unsplash.core.adapter.PhotoDBListAdapter
import com.kotlin.unsplash.databinding.FragmentGalleryBinding
import com.kotlin.unsplash.core.util.OnClickListener
import com.kotlin.unsplash.features.presentation.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

        val application = requireNotNull(this.activity).application

        binding.viewModel = viewModel
        val layoutManager = GridLayoutManager(application, 3)
        val adapter = PhotoDBListAdapter(OnClickListener { viewModel.photoDetails(it) })

        binding.recyclerPhotoDB.adapter = adapter
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToSelectedPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(GalleryFragmentDirections.actionGalleryFragmentToPhotoDBDetailFragment(it))
                viewModel.onNavigateComplete()
            }
        })

        binding.recyclerPhotoDB.layoutManager = layoutManager
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}