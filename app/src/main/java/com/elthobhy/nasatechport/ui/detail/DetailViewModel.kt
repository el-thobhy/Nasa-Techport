package com.elthobhy.nasatechport.ui.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elthobhy.nasatechport.data.TechportRepository
import com.elthobhy.nasatechport.data.remote.TechportResponseItem
import com.elthobhy.nasatechport.di.Injection

class DetailViewModel(private val techportRepository: TechportRepository): ViewModel() {
    fun getDetail(id: String): LiveData<TechportResponseItem> =
        techportRepository.getDetail(id)
}

class ViewModelFactoryDetail(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
