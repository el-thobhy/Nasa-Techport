package com.elthobhy.nasatechport

import android.app.Application
import android.content.ContentValues
import android.util.Log
import com.elthobhy.nasatechport.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyKoinApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e(ContentValues.TAG, "onCreate: MyApp", )
        startKoin{
            androidLogger(Level.NONE)
            androidContext(this@MyKoinApp)
            modules(
                networking,
                databaseModule,
                repositoryModule,
                viewModelModule,
                adapterModule
            )
        }
    }
}