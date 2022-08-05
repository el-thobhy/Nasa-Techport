package com.elthobhy.nasatechport.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.elthobhy.nasatechport.core.domain.model.Apod
import com.elthobhy.nasatechport.core.utils.vo.Resource
import com.elthobhy.nasatechport.core.domain.model.Techport
import kotlinx.coroutines.flow.Flow

interface TechportUsecase {
    fun getData(): Flow<Resource<PagingData<Techport>>>
    fun getApod(): Flow<Resource<List<Apod>>>
    fun getFavorite(): Flow<PagingData<Techport>>
    fun setFavorite(techport: Techport, state: Boolean)
    fun getDetail(id: String): LiveData<Techport>
    fun getDetailApod(title: String): LiveData<Apod>
}