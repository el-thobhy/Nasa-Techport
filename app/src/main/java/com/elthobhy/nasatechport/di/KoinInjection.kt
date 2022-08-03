package com.elthobhy.nasatechport.di

import android.content.Context
import androidx.room.Room
import com.elthobhy.nasatechport.data.TechportRemoteMediator
import com.elthobhy.nasatechport.data.TechportRepository
import com.elthobhy.nasatechport.data.local.TechportDatabase
import com.elthobhy.nasatechport.data.remote.ApiConfig
import com.elthobhy.nasatechport.data.remote.ApiService
import com.elthobhy.nasatechport.ui.adapter.LoadingStateAdapter
import com.elthobhy.nasatechport.ui.adapter.TechportListAdapter
import com.elthobhy.nasatechport.ui.detail.DetailViewModel
import com.elthobhy.nasatechport.ui.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networking = module {
    single {  OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.nasa.gov/resource/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    single { Room.databaseBuilder(
        androidContext(), TechportDatabase::class.java, "techport_database"
    ).fallbackToDestructiveMigration().build()  }
    factory { get<TechportDatabase>().techportDao() }
    factory { get<TechportDatabase>().remoteKeysDao() }
}

val repositoryModule = module {
    single { TechportRemoteMediator(get(),get()) }
    single { TechportRepository(get(),get()) }
}

val viewModelModule = module {
    single { MainViewModel(get()) }
    single { DetailViewModel(get()) }
}

val adapterModule = module {
    single { TechportListAdapter() }
}