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
import com.kotlin.unsplash.R
import com.kotlin.unsplash.core.adapter.TopicListAdapter
import com.kotlin.unsplash.databinding.FragmentTopicBinding
import com.kotlin.unsplash.core.util.OnClickListener
import com.kotlin.unsplash.features.presentation.viewmodel.TopicViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicFragment : Fragment() {
    private lateinit var binding: FragmentTopicBinding
    private val viewModel: TopicViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic, container, false)

        binding.viewModel = viewModel
        val adapter = TopicListAdapter(OnClickListener{
            viewModel.topicDetails(it)
        })

        viewModel.listTopic.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToSelectedTopic.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(TopicFragmentDirections.actionTopicFragmentToTopicPhotoFragment(it))
                viewModel.navigateTopicDetailsComplete()
            }
        })

        binding.recyclerTopic.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}