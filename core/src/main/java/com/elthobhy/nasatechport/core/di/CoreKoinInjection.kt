package com.elthobhy.nasatechport.core.di

import androidx.room.Room
import com.elthobhy.nasatechport.core.domain.repository.ITechportRepository
import com.elthobhy.nasatechport.core.utils.AppExecutors
import com.elthobhy.nasatechport.core.data.TechportRemoteMediator
import com.elthobhy.nasatechport.core.data.TechportRepository
import com.elthobhy.nasatechport.core.data.local.LocalDataSource
import com.elthobhy.nasatechport.core.data.local.room.TechportDatabase
import com.elthobhy.nasatechport.core.data.remote.RemoteDataSource
import com.elthobhy.nasatechport.core.data.remote.network.ApiService
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
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { TechportRemoteMediator(get(),get()) }
    single<ITechportRepository> { TechportRepository(get(),get(), get(), get(), get()) }
    factory { AppExecutors() }
}

