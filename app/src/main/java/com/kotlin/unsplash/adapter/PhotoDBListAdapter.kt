package com.kotlin.unsplash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.unsplash.database.PhotoDB
import com.kotlin.unsplash.databinding.FragmentGalleryBinding
import com.kotlin.unsplash.databinding.ItemImageGalleryBinding
import com.kotlin.unsplash.util.OnClickListener

class PhotoDBListAdapter(private val onClickListener: OnClickListener<PhotoDB>): ListAdapter<PhotoDB, PhotoDBListAdapter.ViewHolder>(PhotoDBDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onClickListener.clickListener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemImageGalleryBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: PhotoDB){
            binding.photoDB = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageGalleryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    class PhotoDBDiffCallback: DiffUtil.ItemCallback<PhotoDB>() {
        override fun areContentsTheSame(oldItem: PhotoDB, newItem: PhotoDB): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PhotoDB, newItem: PhotoDB): Boolean {
            return oldItem.id == newItem.id
        }
    }
}