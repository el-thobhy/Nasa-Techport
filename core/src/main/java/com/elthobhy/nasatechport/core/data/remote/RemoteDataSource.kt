package com.elthobhy.nasatechport.core.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.elthobhy.nasatechport.core.BuildConfig
import com.elthobhy.nasatechport.core.data.local.entity.ApodEntity
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.remote.network.ApiService
import com.elthobhy.nasatechport.core.data.remote.network.ApiServiceApod
import com.elthobhy.nasatechport.core.data.remote.response.ApodResponseItem
import com.elthobhy.nasatechport.core.data.remote.response.vo.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.util.*

class RemoteDataSource(
    private val apiService: ApiService,
    private val apiServiceApod: ApiServiceApod
) {
    suspend fun getAllData(): Flow<ApiResponse<List<TechportEntity>>>{
        return flow {
            try {
                val response = apiService.getData()
                if(response.isNotEmpty()){
                    emit(ApiResponse.success(response))
                }else{
                    emit(ApiResponse.error())
                }
            }catch (e: Exception){
                emit(ApiResponse.error(e.toString()))
                Log.e("RemoteDataSource", "getAllData: ${e.message.toString()}" )
            } as Unit
        }.flowOn(Dispatchers.IO)
    }

   @RequiresApi(Build.VERSION_CODES.O)
   suspend fun getApodData(): Flow<ApiResponse<List<ApodResponseItem>>>{
        return flow {
            try {
                val end = LocalDate.now()
                val start = LocalDate.now().minusDays(7)
                val response = apiServiceApod.getApod(startDate = start, endDate = end)
                Log.e("remote", "getApodData: $response", )
                emit(ApiResponse.success(response))
            }catch (e: Exception){
                emit(ApiResponse.error(e.toString()))
                Log.e("RemoteData", "getApodData: ${e.message.toString()}", )
            }
        }.flowOn(Dispatchers.IO)
    }

}