package com.elthobhy.nasatechport.di

import com.elthobhy.nasatechport.core.domain.usecase.TechportInteractor
import com.elthobhy.nasatechport.core.domain.usecase.TechportUsecase
import com.elthobhy.nasatechport.detail.DetailViewModel
import com.elthobhy.nasatechport.main.MainViewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TechportUsecase> { TechportInteractor(get()) }
}

val viewModelModule = module {
    single {MainViewModel(get())}
    single {DetailViewModel(get())}
}