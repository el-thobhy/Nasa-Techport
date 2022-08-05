package com.elthobhy.nasatechport.core.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elthobhy.nasatechport.core.data.local.entity.ApodEntity
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TechportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(techport: List<TechportEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApod(apodEntity: List<ApodEntity>)

    @Query("SELECT * FROM tech_port")
    fun getData(): PagingSource<Int, TechportEntity>

    @Query("SELECT * FROM apod_entity")
    fun getDataApod(): Flow<List<ApodEntity>>

    @Query("DELETE FROM tech_port")
    suspend fun deleteAll()

    @Query("SELECT * FROM tech_port WHERE projectid = :projectId")
    fun getDetail(projectId: String): LiveData<TechportEntity>

    @Query("SELECT * FROM tech_port WHERE isFavorite = 1")
    fun getFavorite(): PagingSource<Int,TechportEntity>
}