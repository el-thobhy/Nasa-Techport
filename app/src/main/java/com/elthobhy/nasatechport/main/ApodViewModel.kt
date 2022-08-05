package com.elthobhy.nasatechport.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.elthobhy.nasatechport.core.domain.usecase.TechportUsecase

class ApodViewModel(private val techportUsecase: TechportUsecase): ViewModel() {
    fun apod() = techportUsecase.getApod().asLiveData()
}