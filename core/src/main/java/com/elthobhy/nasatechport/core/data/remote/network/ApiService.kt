package com.elthobhy.nasatechport.core.data.remote.network

import com.elthobhy.nasatechport.core.BuildConfig
import com.elthobhy.nasatechport.core.data.local.entity.ApodEntity
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.remote.response.ApodResponseItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.time.LocalDate
import java.util.*

interface ApiService {
   @GET("bq5k-hbdz.json")
   suspend fun getData(): List<TechportEntity>
}

interface ApiServiceApod{
   @GET("/planetary/apod?api_key=${BuildConfig.API_KEY}")
   suspend fun getApod(
      @Query("start_date") startDate : LocalDate? = null,
      @Query("end_date") endDate: LocalDate? = null
   ): List<ApodResponseItem>
}