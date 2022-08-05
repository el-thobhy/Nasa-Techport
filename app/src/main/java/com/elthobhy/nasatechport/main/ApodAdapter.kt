package com.elthobhy.nasatechport.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.elthobhy.nasatechport.R
import com.elthobhy.nasatechport.core.databinding.ItemApodBinding
import com.elthobhy.nasatechport.core.domain.model.Apod

class ApodAdapter: ListAdapter<Apod, ApodAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemApodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemApodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Apod?) {
            binding.apply {
                Glide.with(itemView)
                    .load(data?.hdurl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.color.white)
                    .into(imageApod)
                titleApod.text = data?.title
                copyright.text = data?.copyright
                date.text = data?.date
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Apod>() {
            override fun areItemsTheSame(oldItem: Apod, newItem: Apod): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Apod, newItem: Apod): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

}