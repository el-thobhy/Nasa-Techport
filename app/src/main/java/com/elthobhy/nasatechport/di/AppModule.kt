package com.elthobhy.nasatechport.di

import com.elthobhy.nasatechport.core.domain.usecase.TechportInteractor
import com.elthobhy.nasatechport.core.domain.usecase.TechportUsecase
import com.elthobhy.nasatechport.detail.DetailViewModel
import com.elthobhy.nasatechport.main.ApodAdapter
import com.elthobhy.nasatechport.main.ApodViewModel
import com.elthobhy.nasatechport.main.MainViewModel
import com.elthobhy.nasatechport.main.TechportListAdapter
import com.elthobhy.nasatechport.search.SearchAdapter
import com.elthobhy.nasatechport.search.SearchViewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TechportUsecase> { TechportInteractor(get()) }
}

val viewModelModule = module {
    single {MainViewModel(get())}
    single {DetailViewModel(get())}
    single { ApodViewModel(get()) }
    single { SearchViewModel(get()) }
}
val adapterModule = module {
    single { TechportListAdapter() }
    single { ApodAdapter() }
    single { SearchAdapter() }
}