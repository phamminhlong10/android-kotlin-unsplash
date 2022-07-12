package com.kotlin.unsplash.features.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kotlin.unsplash.R
import com.kotlin.unsplash.core.adapter.PhotoListAdapter
import com.kotlin.unsplash.databinding.FragmentMainBinding
import com.kotlin.unsplash.core.util.OnClickListener
import com.kotlin.unsplash.features.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application

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