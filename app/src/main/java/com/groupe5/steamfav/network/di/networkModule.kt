package com.groupe5.steamfav.network.di

import com.groupe5.steamfav.network.abstraction.SteamCommunityDataSource
import com.groupe5.steamfav.network.abstraction.SteamStoreDataSource
import com.groupe5.steamfav.network.abstraction.SteamWorksWebDataSource
import com.groupe5.steamfav.network.services.SteamCommunityNetwork
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.network.services.SteamWorksWebNetwork
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::SteamWorksWebNetwork) { bind<SteamWorksWebDataSource>() }
    singleOf(::SteamStoreNetwork) { bind<SteamStoreDataSource>() }
    singleOf(::SteamCommunityNetwork) { bind<SteamCommunityDataSource>() }
}
