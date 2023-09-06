package com.jhkim.kko.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhkim.core.model.ImageResource
import com.jhkim.kko.R
import com.jhkim.kko.databinding.ImageItemBinding

class ImageResourceAdapter(private val onClick: (ImageResource) -> Unit) :
    ListAdapter<ImageResource, ImageResourceAdapter.ImageViewHolder>(ImageResourceDiffCallback) {

    class ImageViewHolder(
        private val binding: ImageItemBinding,
        private val onClick: (ImageResource) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentImageResource: ImageResource? = null

        init {
            binding.root.setOnClickListener {
                currentImageResource?.let(onClick)
            }
        }

        fun bind(imageResource: ImageResource) {
            currentImageResource = imageResource
            binding.imageResource = imageResource
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.image_item, parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResource = getItem(position)
        holder.bind(imageResource)
    }
}

object ImageResourceDiffCallback : DiffUtil.ItemCallback<ImageResource>() {
    override fun areItemsTheSame(oldItem: ImageResource, newItem: ImageResource): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageResource, newItem: ImageResource): Boolean {
        return oldItem.url == newItem.url
    }

}
