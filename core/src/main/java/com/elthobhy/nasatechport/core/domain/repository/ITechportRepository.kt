package com.elthobhy.nasatechport.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.elthobhy.nasatechport.core.domain.model.Apod
import com.elthobhy.nasatechport.core.domain.model.ApodTechportDomain
import com.elthobhy.nasatechport.core.domain.model.Techport
import com.elthobhy.nasatechport.core.utils.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ITechportRepository {

    fun getData(): Flow<Resource<PagingData<Techport>>>

    fun getApod(): Flow<Resource<List<Apod>>>

    fun getSearch(search: String?): Flow<Resource<List<ApodTechportDomain>>>

    fun getDetail(id: String): LiveData<Techport>

    fun getDetailApod(title: String): LiveData<Apod>

    fun getFavorite(): Flow<PagingData<Techport>>

    fun setFavorite(techport: Techport, state: Boolean)

}