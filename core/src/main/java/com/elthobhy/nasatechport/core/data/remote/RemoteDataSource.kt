package com.elthobhy.nasatechport.core.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.elthobhy.nasatechport.core.data.local.LocalDataSource
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.remote.network.ApiService
import com.elthobhy.nasatechport.core.data.remote.network.ApiServiceApod
import com.elthobhy.nasatechport.core.data.remote.response.ApodResponseItem
import com.elthobhy.nasatechport.core.data.remote.response.ApodTechportResponse
import com.elthobhy.nasatechport.core.data.remote.response.vo.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class RemoteDataSource(
    private val apiService: ApiService,
    private val apiServiceApod: ApiServiceApod,
    private val localDataSource: LocalDataSource
) {
    suspend fun getAllData(): Flow<ApiResponse<List<TechportEntity>>>{
        return flow {
            try {
                val response = apiService.getData()
                    emit(ApiResponse.success(response))
            }catch (e: Exception){
                emit(ApiResponse.error(e.toString()))
                Log.e("RemoteDataSource", "getAllData: ${e.message.toString()}" )
            }
        }.flowOn(Dispatchers.IO)
    }

   @RequiresApi(Build.VERSION_CODES.O)
   suspend fun getApodData(): Flow<ApiResponse<List<ApodResponseItem>>>{
        return flow {
            try {
                val end = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US))
                val start = end.minusDays(7)
                val response = apiServiceApod.getApod(startDate = start, endDate = end)
                emit(ApiResponse.success(response))
            }catch (e: Exception){
                emit(ApiResponse.error(e.toString()))
                Log.e("RemoteData", "getApodData: ${e.message.toString()}" )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearch(): Flow<ApiResponse<List<ApodTechportResponse>>>{
        return flow{
            try{
                val responseApod = localDataSource.getDataApod()
                val responseTechport = localDataSource.getDataTech()
                val resposeApodTechportResponse = ArrayList<ApodTechportResponse>()
                responseApod.map {
                    val apod = ApodTechportResponse(
                        title = it.title,
                        name = it.copyright,
                        date = it.date,
                        image = it.url,
                    )
                    resposeApodTechportResponse.add(apod)
                }
                responseTechport.map{
                    val techport = ApodTechportResponse(
                        title = it.title,
                        name = it.responsiblenasaprogram,
                        date = it.lastupdated,
                        projectId = it.projectid
                    )
                    resposeApodTechportResponse.add(techport)
                }
                emit(ApiResponse.success(resposeApodTechportResponse.toList()))
            }catch (e: Exception){
                emit(ApiResponse.error(e.toString()))
                Log.e("remote", "getBoth: ${e.message.toString()}" )
            }
        }.flowOn(Dispatchers.IO)
    }

}