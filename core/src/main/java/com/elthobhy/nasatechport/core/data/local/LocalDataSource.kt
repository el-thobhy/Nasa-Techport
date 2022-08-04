package com.elthobhy.nasatechport.core.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.local.room.TechportDao

class LocalDataSource(private val techportDao: TechportDao) {
    fun getData() : PagingSource<Int, TechportEntity> = techportDao.getData()

    fun getFavoriteTechport(): PagingSource<Int, TechportEntity> = techportDao.getFavorite()

    suspend fun insertTechport(techportResponse: List<TechportEntity>) = techportDao.insertData(techportResponse)

    fun setFavorite(techportEntity: TechportEntity, newState: Boolean){
        techportEntity.isFavorite = newState
        techportDao.getFavorite()
    }

}