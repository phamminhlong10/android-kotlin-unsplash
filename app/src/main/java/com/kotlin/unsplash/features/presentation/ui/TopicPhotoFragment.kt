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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.core.adapter.PhotoListAdapter
import com.kotlin.unsplash.databinding.FragmentTopicPhotoBinding
import com.kotlin.unsplash.core.util.OnClickListener
import com.kotlin.unsplash.features.presentation.viewmodel.TopicPhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicPhotoFragment : Fragment() {

    private lateinit var binding: FragmentTopicPhotoBinding
    private val viewModel: TopicPhotoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_photo, container, false)
        setHasOptionsMenu(true)

        val topicFragmentArgs by navArgs<TopicPhotoFragmentArgs>()

        binding.viewModel = viewModel


        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.recyclerTopicPhoto.layoutManager = layoutManager


        val adapter = PhotoListAdapter(OnClickListener {
            viewModel.photoDetails(it)
        })

        viewModel.navigateToSelectedPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(TopicPhotoFragmentDirections.actionTopicPhotoFragmentToPhotoDetailFragment(it))
                viewModel.navigatePhotoDetailsComplete()
            }
        })


        lifecycleScope.launch {
            viewModel.photo.collectLatest {
                adapter.submitData(it)
            }
        }
        binding.recyclerTopicPhoto.adapter = adapter

        (activity as MainActivity).supportActionBar?.title = topicFragmentArgs.topic.title
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}