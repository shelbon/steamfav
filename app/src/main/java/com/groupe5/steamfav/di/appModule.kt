package com.groupe5.steamfav.di

import com.groupe5.steamfav.data.di.dataModule
import com.groupe5.steamfav.network.di.networkModule
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel
import com.groupe5.steamfav.viewmodels.HomeViewModel
import com.groupe5.steamfav.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(
        networkModule,
        dataModule
    )
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::GameDetailsViewModel)
}