package com.kotlin.unsplash.features.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.kotlin.unsplash.MainActivity
import com.kotlin.unsplash.R
import com.kotlin.unsplash.databinding.FragmentPhotoDetailBinding
import com.kotlin.unsplash.core.util.WallpaperEvent
import com.kotlin.unsplash.features.presentation.viewmodel.PhotoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val viewModel: PhotoDetailViewModel by viewModels()
    @Inject lateinit var wallpaperEvent: WallpaperEvent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_detail, container, false)
        val application = requireNotNull(this.activity).application

        viewModel.photo.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                delay(500L)
                if(it != null){
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.circularProgressBar.visibility = View.GONE
                    binding.textError.visibility = View.VISIBLE
                }
            }
        })

        binding.setWallpaperButton.setOnClickListener {
            viewModel.photo.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    wallpaperEvent.showAlertDialog(binding.photoView.drawable.toBitmap(), application,
                        requireNotNull(this@PhotoDetailFragment.context))
                }
            })
        }

        binding.explorePhotoButton.setOnClickListener {
            viewModel.photo.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    wallpaperEvent.explorePhoto(it.id, requireNotNull(this@PhotoDetailFragment.context))
                }
            })
        }

        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(application, "Added favorites", Toast.LENGTH_SHORT).show()
                viewModel.showToastComplete()
            }
        })

        (activity as MainActivity).supportActionBar?.title = ""
        setHasOptionsMenu(true)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorites_button -> binding.viewModel?.onFavoritesClicked()
        }
        return super.onOptionsItemSelected(item)
    }
}