package com.elthobhy.nasatechport.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.elthobhy.nasatechport.core.domain.model.Apod
import com.elthobhy.nasatechport.core.domain.repository.ITechportRepository
import com.elthobhy.nasatechport.core.utils.vo.Resource
import com.elthobhy.nasatechport.core.domain.model.Techport
import kotlinx.coroutines.flow.Flow

class TechportInteractor(private val repository: ITechportRepository): TechportUsecase {

    override fun getData(): Flow<Resource<PagingData<Techport>>> = repository.getData()

    override fun getApod(): Flow<Resource<List<Apod>>> = repository.getApod()

    override fun getSearch(search: String?): Flow<Resource<List<Techport>>> = repository.getSearch(search)

    override fun getFavorite(): Flow<PagingData<Techport>> = repository.getFavorite()

    override fun setFavorite(techport: Techport, state: Boolean) = repository.setFavorite(techport, state)

    override fun getDetail(id: String): LiveData<Techport> = repository.getDetail(id)

    override fun getDetailApod(title: String): LiveData<Apod> = repository.getDetailApod(title)


}