package com.renovavision.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.renovavision.base.R
import com.renovavision.domain.models.Photo
import com.renovavision.photos.databinding.ItemPhotoBinding

class PhotosAdapter(
    private val onItemClickListener: ((Photo) -> Unit)? = null,
    private val onLongItemClickListener: ((Photo) -> Unit)? = null,
) : PagingDataAdapter<Photo, PhotosAdapter.ItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class ItemViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Photo) = with(binding) {
            ivPoster.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
                fallback(R.drawable.image_placeholder)
                error(R.drawable.image_placeholder)
            }

            tvTitle.text = item.photographer

            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }

            root.setOnLongClickListener {
                onLongItemClickListener?.invoke(item)
                true
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
        }
    }
}