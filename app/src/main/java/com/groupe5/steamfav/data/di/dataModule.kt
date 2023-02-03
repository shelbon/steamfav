package com.groupe5.steamfav.data.di

import com.groupe5.steamfav.data.GameReviewsRepository
import com.groupe5.steamfav.data.GamesRepository
import com.groupe5.steamfav.network.di.networkModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    includes(
        networkModule
    )
    singleOf(::GamesRepository) {
        bind<com.groupe5.steamfav.data.abstraction.GamesRepository>()
    }
    singleOf(::GameReviewsRepository) {
        bind<com.groupe5.steamfav.data.abstraction.GameReviewsRepository>()
    }
}
