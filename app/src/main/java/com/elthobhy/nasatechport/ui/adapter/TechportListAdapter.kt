package com.elthobhy.nasatechport.ui.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.nasatechport.data.remote.TechportResponseItem
import com.elthobhy.nasatechport.databinding.ItemQuoteBinding
import com.elthobhy.nasatechport.ui.detail.DetailActivity
import com.elthobhy.nasatechport.utils.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TechportListAdapter :
    PagingDataAdapter<TechportResponseItem, TechportListAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
        fun bind(data: TechportResponseItem) {
            val latestUpdated = data.lastupdated?.split('T')
            val updated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.parse(latestUpdated?.get(0), DateTimeFormatter.ISO_DATE)
            } else {
                data.lastupdated
            }
            binding.tvTitle.text = data.title
            binding.tvDateUpdate.text = updated.toString()
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                    .putExtra(Constants.DATA, data.projectid)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TechportResponseItem>() {
            override fun areItemsTheSame(oldItem: TechportResponseItem, newItem: TechportResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TechportResponseItem, newItem: TechportResponseItem): Boolean {
                return oldItem.projectid == newItem.projectid
            }
        }
    }
}