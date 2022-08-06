package com.elthobhy.nasatechport.core.data.local

import androidx.paging.PagingSource
import com.elthobhy.nasatechport.core.data.local.entity.ApodEntity
import com.elthobhy.nasatechport.core.data.local.entity.ApodTechportEntity
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.local.room.TechportDao
import com.elthobhy.nasatechport.core.data.remote.response.ApodResponseItem
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val techportDao: TechportDao) {
    fun getData() : PagingSource<Int, TechportEntity> = techportDao.getData()

    fun getApod() : Flow<List<ApodEntity>> = techportDao.getDataApod()

    fun getSearch(search: String?) : Flow<List<ApodTechportEntity>> = techportDao.getDataBoth(search)

    fun getFavoriteTechport(): PagingSource<Int, TechportEntity> = techportDao.getFavorite()


    fun getDataApod(): List<ApodEntity> = techportDao.getDataApodSearch()

    fun getDataTech(): List<TechportEntity> = techportDao.getDataTechSearch()


    suspend fun insertTechport(techportResponse: List<TechportEntity>) = techportDao.insertData(techportResponse)

    suspend fun insertApod(apod: List<ApodEntity>) = techportDao.insertApod(apod)

    suspend fun insertApodTechport(apodTechport: List<ApodTechportEntity>) = techportDao.insertApodTech(apodTechport)

    fun setFavorite(techportEntity: TechportEntity, newState: Boolean){
        techportEntity.isFavorite = newState
        techportDao.getFavorite()
    }

}