package com.example.imagesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesapp.R
import com.example.imagesapp.databinding.ItemPhotoBinding
import com.example.imagesapp.models.UnsplashImage

class UnsplashImageAdapter(private val listener:OnItemClickListener):
    PagingDataAdapter<UnsplashImage, UnsplashImageAdapter.UnsplashViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashViewHolder {
        return UnsplashViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: UnsplashViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.bind(image)
        }
    }


    inner class UnsplashViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if(bindingAdapterPosition != RecyclerView.NO_POSITION){
                    getItem(bindingAdapterPosition)?.let { it1 -> listener.onItemClick(it1) }
                }
            }
        }

        fun bind(unsplashImage: UnsplashImage) {
            binding.apply {
                Glide.with(itemView).load(unsplashImage.urls.regular).centerCrop()
                    .error(R.drawable.ic_baseline_error_24).into(imageView)
                username.text = unsplashImage.user.username
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(unsplashImage: UnsplashImage)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<UnsplashImage>() {
            override fun areItemsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashImage,
                newItem: UnsplashImage
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}