package com.jhkim.kko.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhkim.core.model.ImageWithFavoriteStatus
import com.jhkim.kko.R
import com.jhkim.kko.databinding.ImageItemBinding

class ImageResourceAdapter(private val onClick: (ImageWithFavoriteStatus) -> Unit) :
    ListAdapter<ImageWithFavoriteStatus, ImageResourceAdapter.ImageViewHolder>(
        ImageResourceDiffCallback
    ) {

    class ImageViewHolder(
        private val binding: ImageItemBinding,
        private val onClick: (ImageWithFavoriteStatus) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentImageWithFavoriteStatus: ImageWithFavoriteStatus? = null

        init {
            binding.root.setOnClickListener {
                currentImageWithFavoriteStatus?.let(onClick)
            }
        }

        fun bind(imageWithFavoriteStatus: ImageWithFavoriteStatus) {
            currentImageWithFavoriteStatus = imageWithFavoriteStatus
            binding.imageWithFavoriteStatus = imageWithFavoriteStatus
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.image_item,
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResource = getItem(position)
        holder.bind(imageResource)
    }
}

object ImageResourceDiffCallback : DiffUtil.ItemCallback<ImageWithFavoriteStatus>() {
    override fun areItemsTheSame(
        oldItem: ImageWithFavoriteStatus,
        newItem: ImageWithFavoriteStatus
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ImageWithFavoriteStatus,
        newItem: ImageWithFavoriteStatus
    ): Boolean {
        return oldItem.imageResource.url == newItem.imageResource.url
    }

}
