package com.groupe5.steamfav.data.di

import com.google.firebase.auth.FirebaseAuth
import com.groupe5.steamfav.data.GamesRepository
import com.groupe5.steamfav.data.impl.AuthRepository
import com.groupe5.steamfav.network.di.networkModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule= module{
    includes(
        networkModule
    )
    single{
        FirebaseAuth.getInstance()
    }
    singleOf(::GamesRepository){
        bind<com.groupe5.steamfav.data.abstraction.GamesRepository>()
    }
    singleOf(::AuthRepository){
        bind<com.groupe5.steamfav.data.abstraction.AuthRepository>()
    }
}
