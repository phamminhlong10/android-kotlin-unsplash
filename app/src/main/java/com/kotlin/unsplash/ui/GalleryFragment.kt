package com.kotlin.unsplash.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.unsplash.R
import com.kotlin.unsplash.adapter.PhotoDBListAdapter
import com.kotlin.unsplash.database.PhotoDB
import com.kotlin.unsplash.database.PhotoDatabase
import com.kotlin.unsplash.databinding.FragmentGalleryBinding
import com.kotlin.unsplash.util.OnClickListener
import com.kotlin.unsplash.viewmodel.GalleryViewModel
import com.kotlin.unsplash.viewmodel.GalleryViewModelFactory


class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

        val application = requireNotNull(this.activity).application
        val database = PhotoDatabase.getInstance(application).photoDao
        val viewModel = ViewModelProvider(this, GalleryViewModelFactory(database, application)).get(GalleryViewModel::class.java)

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