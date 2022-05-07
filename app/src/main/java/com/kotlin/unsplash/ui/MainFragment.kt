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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kotlin.unsplash.R
import com.kotlin.unsplash.adapter.PhotoListAdapter
import com.kotlin.unsplash.databinding.FragmentMainBinding
import com.kotlin.unsplash.viewmodel.FirstViewModel


class MainFragment : Fragment() {
    companion object{
        const val TWITTER = "https://pbs.twimg.com/media/"
    }
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)

        binding.viewModel = viewModel
        val adapter = PhotoListAdapter()
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerImage.layoutManager = layoutManager

        viewModel.listPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.recyclerImage.adapter = adapter

//        lifecycleScope.launch {
//            var listResult = UnsplashApi.retrofitService.getListPhoto(CLIENT_ID)
//            Log.e("Results", listResult.toString())
//        }

        binding.lifecycleOwner = this
        return binding.root
    }

//    private val listAdapter: List<Image> = arrayListOf(
//        Image(1, TWITTER+"FQwQQQ2VIAEFb9C?format=jpg&name=small", "HazelMoore"),
//        Image(2, TWITTER+"FR8BC9CWQAIVG29?format=jpg&name=large", "Nekolul"),
//        Image(3, TWITTER+"FR8BC9kXsAAODuD?format=jpg&name=large", "Nekolul"),
//        Image(4, TWITTER+"FQv_OruVgAM3Qqv?format=jpg&name=large", "Kimi"),
//        Image(5, TWITTER+"FNl0PRlVUAYL9Qz?format=jpg&name=large", "Kimi"),
//        Image(6, TWITTER+"FObBxhJVgAEqen5?format=jpg&name=large", "Kimi"),
//        Image(7, TWITTER+"EmWtpVdUYAAuaaP?format=jpg&name=large", "WaterLyn"),
//        Image(8, TWITTER+"EiaToYWUYAUWXVb?format=jpg&name=large", "WaterLyn"),
//        Image(9, TWITTER+"FLnm6ibaQAIv9H6?format=jpg&name=large", "Jinnytty"),
//        Image(10, TWITTER+"FI-uKGGWUAMib9W?format=jpg&name=large", "Jinnytty"),
//        Image(11, TWITTER+"FHbHaZnUYAETBR3?format=jpg&name=large", "Tinakitten"),
//        Image(12, TWITTER+"FCbD2mmVQAEJoV8?format=jpg&name=large", "Tinakitten"),
//    )
}