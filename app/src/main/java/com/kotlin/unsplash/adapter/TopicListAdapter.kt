package com.kotlin.unsplash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.unsplash.databinding.ItemTopicBinding
import com.kotlin.unsplash.models.Topic
import com.kotlin.unsplash.util.OnClickListener

class TopicListAdapter(private val onClickListener: OnClickListener<Topic>): ListAdapter<Topic, TopicListAdapter.ViewHolder>(DiffCallBackTopic()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.clickListener(item)
        }
    }

    class ViewHolder(private val binding: ItemTopicBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic){
            binding.topic = topic
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTopicBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallBackTopic(): DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }
    }
}