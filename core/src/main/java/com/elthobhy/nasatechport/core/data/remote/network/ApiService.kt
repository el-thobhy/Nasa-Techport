package com.elthobhy.nasatechport.core.data.remote.network

import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import retrofit2.http.GET

interface ApiService {
   @GET("bq5k-hbdz.json")
   suspend fun getData(): List<TechportEntity>
}