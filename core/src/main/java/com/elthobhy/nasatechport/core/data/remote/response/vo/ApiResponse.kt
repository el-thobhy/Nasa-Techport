package com.elthobhy.nasatechport.core.data.remote.response.vo

class ApiResponse<T>(val statusNetwork: StatusResponseNetwork, val body: T?, val message: String?) {
    companion object {
        fun <T> success(body: T?): ApiResponse<T> =
            ApiResponse(StatusResponseNetwork.SUCCESS, body, null)

        fun <T> error(msg: String? =null, body: T?=null): ApiResponse<T> =
            ApiResponse(StatusResponseNetwork.ERROR, body, msg)
    }
}