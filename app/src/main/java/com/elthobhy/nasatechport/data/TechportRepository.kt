package com.elthobhy.nasatechport.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.elthobhy.nasatechport.data.local.TechportDatabase
import com.elthobhy.nasatechport.data.remote.ApiService
import com.elthobhy.nasatechport.data.remote.TechportResponseItem

class TechportRepository(private val techportDatabase: TechportDatabase, private val apiService: ApiService) {
    fun getData(): LiveData<PagingData<TechportResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = TechportRemoteMediator(techportDatabase, apiService),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                techportDatabase.techportDao().getData()
            }
        ).liveData
    }
    fun getDetail(id: String): LiveData<TechportResponseItem>{
        return techportDatabase.techportDao().getDetail(id)
    }
}