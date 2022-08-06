package com.elthobhy.nasatechport.search

import androidx.lifecycle.asLiveData
import com.elthobhy.nasatechport.core.domain.usecase.TechportUsecase

class SearchViewModel(private val techportUsecase: TechportUsecase) {
    fun getData(string: String) = techportUsecase.getSearch(string).asLiveData()
}