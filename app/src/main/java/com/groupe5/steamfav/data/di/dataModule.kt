package com.groupe5.steamfav.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.groupe5.steamfav.data.impl.AuthRepository
import com.groupe5.steamfav.data.impl.GameReviewsRepository
import com.groupe5.steamfav.data.impl.GamesRepository
import com.groupe5.steamfav.data.impl.LikedGamesRepository
import com.groupe5.steamfav.data.impl.WishedGameRepository
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
    single {
        FirebaseAuth.getInstance()
    }
    single {
        FirebaseFirestore.getInstance()
    }
    singleOf(::GameReviewsRepository) {
        bind<com.groupe5.steamfav.data.abstraction.GameReviewsRepository>()
    }
    singleOf(::AuthRepository) {
        bind<com.groupe5.steamfav.data.abstraction.AuthRepository>()
    }
    singleOf(::LikedGamesRepository) {
        bind<com.groupe5.steamfav.data.abstraction.LikedGamesRepository>()
    }
    singleOf(::WishedGameRepository) {
        bind<com.groupe5.steamfav.data.abstraction.WishlistGameRepository>()
    }

}
