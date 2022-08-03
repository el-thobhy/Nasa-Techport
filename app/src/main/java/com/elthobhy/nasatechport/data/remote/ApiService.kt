package com.elthobhy.nasatechport.data.remote

import retrofit2.http.GET

interface ApiService {
   @GET("bq5k-hbdz.json")
   suspend fun getQuote(): List<TechportResponseItem>
}