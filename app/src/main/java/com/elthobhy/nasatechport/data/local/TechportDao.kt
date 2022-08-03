package com.elthobhy.nasatechport.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elthobhy.nasatechport.data.remote.TechportResponseItem

@Dao
interface TechportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(quote: List<TechportResponseItem>)

    @Query("SELECT * FROM tech_port")
    fun getData(): PagingSource<Int, TechportResponseItem>

    @Query("DELETE FROM tech_port")
    suspend fun deleteAll()

    @Query("SELECT * FROM tech_port WHERE projectid = :projectId")
    fun getDetail(projectId: String): LiveData<TechportResponseItem>
}