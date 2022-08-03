package com.elthobhy.nasatechport.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elthobhy.nasatechport.data.TechportRepository
import com.elthobhy.nasatechport.di.Injection
import com.elthobhy.nasatechport.data.remote.TechportResponseItem

class MainViewModel(techportRepository: TechportRepository) : ViewModel() {

    val quote: LiveData<PagingData<TechportResponseItem>> =
        techportRepository.getData().cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}