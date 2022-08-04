package com.elthobhy.nasatechport.core.data.remote

import android.util.Log
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.remote.network.ApiService
import com.elthobhy.nasatechport.core.data.remote.response.vo.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
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
                Log.e("RemoteDataSource", "getAllData: ${e.message.toString()}", )
            } as Unit
        }.flowOn(Dispatchers.IO)
    }

}