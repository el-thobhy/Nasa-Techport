package com.elthobhy.nasatechport.core.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elthobhy.nasatechport.core.data.local.LocalDataSource
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.local.room.TechportDatabase
import com.elthobhy.nasatechport.core.data.remote.RemoteDataSource
import com.elthobhy.nasatechport.core.data.remote.network.ApiService
import com.elthobhy.nasatechport.core.data.remote.response.ApodResponseItem
import com.elthobhy.nasatechport.core.data.remote.response.ApodTechportResponse
import com.elthobhy.nasatechport.core.data.remote.response.vo.ApiResponse
import com.elthobhy.nasatechport.core.domain.model.Apod
import com.elthobhy.nasatechport.core.domain.model.ApodTechportDomain
import com.elthobhy.nasatechport.core.domain.model.Techport
import com.elthobhy.nasatechport.core.domain.repository.ITechportRepository
import com.elthobhy.nasatechport.core.utils.AppExecutors
import com.elthobhy.nasatechport.core.utils.DataMapper
import com.elthobhy.nasatechport.core.utils.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TechportRepository(
    private val techportDatabase: TechportDatabase,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource,
    private val apiService: ApiService
): ITechportRepository {



    @OptIn(ExperimentalPagingApi::class)
    override fun getData(): Flow<Resource<PagingData<Techport>>> =
        object : NetworkBoundResource<PagingData<Techport>, List<TechportEntity>>(){
            override suspend fun loadFromDb(): Flow<PagingData<Techport>> {
                return Pager(
                    config = PagingConfig(
                        pageSize = 5
                    ),
                    remoteMediator = TechportRemoteMediator(techportDatabase, apiService),
                    pagingSourceFactory = {
                        localDataSource.getData()
                    }
                ).flow.map { DataMapper.mapPagingEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: PagingData<Techport>?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TechportEntity>>> {
                return remoteDataSource.getAllData()
            }

            override suspend fun saveCallResult(data: List<TechportEntity>) {
                return localDataSource.insertTechport(data)
            }


        }.asFlow()

    override fun getApod(): Flow<Resource<List<Apod>>> =
        object : NetworkBoundResource<List<Apod>, List<ApodResponseItem>>(){
            override suspend fun loadFromDb(): Flow<List<Apod>> {
               return localDataSource.getApod().map {
                   DataMapper.mapApodToDomain(it)
               }
            }

            override fun shouldFetch(data: List<Apod>?): Boolean {
                return data == null || data.isEmpty()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override suspend fun createCall(): Flow<ApiResponse<List<ApodResponseItem>>> {
                return remoteDataSource.getApodData()
            }

            override suspend fun saveCallResult(data: List<ApodResponseItem>) {
                val response = DataMapper.mapApodResponToDomain(data)
                return localDataSource.insertApod(response)
            }
        }.asFlow()

    override fun getSearch(search: String?): Flow<Resource<List<ApodTechportDomain>>> =
        object : NetworkBoundResource<List<ApodTechportDomain>, List<ApodTechportResponse>>(){
            override suspend fun loadFromDb(): Flow<List<ApodTechportDomain>> {
                return localDataSource.getSearch(search).map { DataMapper.mapSearchEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<ApodTechportDomain>?): Boolean {
                return data == null || data.isEmpty()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override suspend fun createCall(): Flow<ApiResponse<List<ApodTechportResponse>>> {
                return remoteDataSource.getBoth()
            }

            override suspend fun saveCallResult(data: List<ApodTechportResponse>) {
                val dataMap = DataMapper.mapSearchResponsesToEntities(data)
                return localDataSource.insertApodTechport(dataMap)
            }

        }.asFlow()





    @OptIn(ExperimentalPagingApi::class)
    override fun getFavorite(): Flow<PagingData<Techport>> {
        return return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = TechportRemoteMediator(techportDatabase, apiService),
            pagingSourceFactory = {
                localDataSource.getFavoriteTechport()
            }
        ).flow.map { DataMapper.mapPagingEntitiesToDomain(it) }
    }

    override fun setFavorite(techport: Techport, state: Boolean) {
        val entity = DataMapper.mapDomainToEntity(techport)
        return appExecutors.diskIO().execute{localDataSource.setFavorite(entity, state)}
    }

    override fun getDetail(id: String): LiveData<Techport>{
        return techportDatabase.techportDao().getDetail(id).map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun getDetailApod(title: String): LiveData<Apod> {
        return techportDatabase.techportDao().getDetailApod(title).map { DataMapper.mapApodEntitiesToDomain(it) }
    }
}