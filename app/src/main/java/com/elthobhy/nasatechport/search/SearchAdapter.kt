package com.elthobhy.nasatechport.search

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.nasatechport.core.databinding.ItemQuoteBinding
import com.elthobhy.nasatechport.core.domain.model.ApodTechportDomain
import com.elthobhy.nasatechport.core.utils.Constants
import com.elthobhy.nasatechport.detail.DetailActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchAdapter: ListAdapter<ApodTechportDomain, SearchAdapter.MyViewHolder>(DIFF_CALLBACK){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ApodTechportDomain?) {
            binding.apply {
                if(data?.date?.contains("T") == true){
                    val latestUpdated = data.date?.split('T')
                    val updated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDate.parse(latestUpdated?.get(0), DateTimeFormatter.ISO_DATE)
                    } else {
                        data.date
                    }
                    tvDateUpdate.text = updated.toString()
                }else{
                    tvDateUpdate.text = data?.date
                }

                tvTitle.text = data?.title
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                        .putExtra(Constants.DATA, data?.projectId)
                        .putExtra(Constants.APOD, data?.title)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApodTechportDomain>() {
            override fun areItemsTheSame(oldItem: ApodTechportDomain, newItem: ApodTechportDomain): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ApodTechportDomain, newItem: ApodTechportDomain): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}