package com.renovavision.videos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.renovavision.base.R
import com.renovavision.domain.models.Video
import com.renovavision.videos.databinding.ItemVideoBinding

class VideosAdapter(
    private val onItemClickListener: ((Video) -> Unit)? = null
) : PagingDataAdapter<Video, VideosAdapter.ItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class ItemViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Video) = with(binding) {
            ivPoster.load(item.image) {
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
                fallback(R.drawable.image_placeholder)
                error(R.drawable.image_placeholder)
            }

            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Video, newItem: Video) = oldItem == newItem
        }
    }
}