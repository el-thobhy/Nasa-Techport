package com.elthobhy.nasatechport.core.data

import com.elthobhy.nasatechport.core.data.remote.response.vo.ApiResponse
import com.elthobhy.nasatechport.core.data.remote.response.vo.StatusResponseNetwork
import com.elthobhy.nasatechport.core.utils.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result : Flow<Resource<ResultType>> = flow {
        emit(Resource.loading(null))
        val dbSource = loadFromDb().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.loading(dbSource))
            val apiResponse = createCall().first()
            when (apiResponse.statusNetwork) {
                StatusResponseNetwork.SUCCESS  ->{
                    apiResponse.body?.let { saveCallResult(it) }
                    emitAll(loadFromDb().map { Resource.success(it) })
                }
                StatusResponseNetwork.EMPTY-> {
                    emitAll(loadFromDb().map { Resource.success(it) })
                }
                StatusResponseNetwork.ERROR-> {
                    onFetchFailed()
                    emit(Resource.error(apiResponse.message+" | OFFLINE MODE |"))
                    emitAll(loadFromDb().map { Resource.success(it) })
                }
            }
        } else {
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun loadFromDb(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}