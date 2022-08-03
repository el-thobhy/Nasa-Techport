package com.elthobhy.nasatechport.di

import android.content.Context
import com.elthobhy.nasatechport.data.TechportRepository
import com.elthobhy.nasatechport.data.local.TechportDatabase
import com.elthobhy.nasatechport.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): TechportRepository {
        val database = TechportDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return TechportRepository(database, apiService)
    }
}