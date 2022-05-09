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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.unsplash.R
import com.kotlin.unsplash.adapter.TopicListAdapter
import com.kotlin.unsplash.databinding.FragmentTopicBinding
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.service.UnsplashService
import com.kotlin.unsplash.viewmodel.TopicViewModel
import com.kotlin.unsplash.viewmodel.TopicViewModelFactory
import kotlinx.coroutines.launch


class TopicFragment : Fragment() {
    private lateinit var binding: FragmentTopicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic, container, false)
        val unsplashService = UnsplashApi.retrofitService
        val application = requireNotNull(this.activity).application

        val viewModel = ViewModelProvider(this, TopicViewModelFactory(unsplashService, application)).get(TopicViewModel::class.java)
        val adapter = TopicListAdapter()

        viewModel.listTopic.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.recyclerTopic.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}