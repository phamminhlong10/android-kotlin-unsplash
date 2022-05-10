package com.kotlin.unsplash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.unsplash.databinding.ItemImageBinding
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.domain.Topic

class PhotoListAdapter: PagingDataAdapter<Photo, PhotoListAdapter.ViewHolder>(PhotoDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class PhotoDiffCallback: DiffUtil.ItemCallback<Photo>() {
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo){
            binding.photo = photo
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}