package com.elthobhy.nasatechport.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elthobhy.nasatechport.data.TechportRepository
import com.elthobhy.nasatechport.data.remote.TechportResponseItem

class MainViewModel(techportRepository: TechportRepository) : ViewModel() {

    val quote: LiveData<PagingData<TechportResponseItem>> =
        techportRepository.getData().cachedIn(viewModelScope)
}

