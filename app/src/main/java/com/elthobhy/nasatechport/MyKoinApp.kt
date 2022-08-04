package com.elthobhy.nasatechport

import android.app.Application
import com.elthobhy.nasatechport.core.di.*
import com.elthobhy.nasatechport.di.useCaseModule
import com.elthobhy.nasatechport.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyKoinApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.NONE)
            androidContext(this@MyKoinApp)
            modules(
                listOf(
                    networking,
                    databaseModule,
                    repositoryModule,
                    viewModelModule,
                    adapterModule,
                    useCaseModule
                )
            )
        }
    }
}