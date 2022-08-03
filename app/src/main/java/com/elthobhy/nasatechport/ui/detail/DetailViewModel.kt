package com.elthobhy.nasatechport.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elthobhy.nasatechport.data.TechportRepository
import com.elthobhy.nasatechport.data.remote.TechportResponseItem

class DetailViewModel(private val techportRepository: TechportRepository): ViewModel() {
    fun getDetail(id: String): LiveData<TechportResponseItem> =
        techportRepository.getDetail(id)
}

