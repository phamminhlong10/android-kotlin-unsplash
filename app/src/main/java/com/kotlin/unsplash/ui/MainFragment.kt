package com.kotlin.unsplash.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kotlin.unsplash.R
import com.kotlin.unsplash.adapter.PhotoListAdapter
import com.kotlin.unsplash.databinding.FragmentMainBinding
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.util.OnClickListener
import com.kotlin.unsplash.viewmodel.MainViewModel
import com.kotlin.unsplash.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val unsplashService = UnsplashApi.retrofitService
        val application = requireNotNull(this.activity).application
        val viewModel = ViewModelProvider(this, MainViewModelFactory(unsplashService, application)).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        val adapter = PhotoListAdapter(OnClickListener {
            viewModel.photoDetails(it)
        })
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.recyclerImage.layoutManager = layoutManager


        lifecycleScope.launch {
            viewModel.photo.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.navigateToSelectedPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToPhotoDetailFragment(it))
                viewModel.navigatePhotoDetailsComplete()
            }
        })

        binding.recyclerImage.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}