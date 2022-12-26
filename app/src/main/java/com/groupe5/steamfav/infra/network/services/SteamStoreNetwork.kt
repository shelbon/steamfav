package com.groupe5.steamfav.infra.network.services

import com.groupe5.steamfav.infra.network.abstraction.RetrofitSteamStoreWebApi
import com.groupe5.steamfav.infra.network.abstraction.SteamStoreDataSource
import com.groupe5.steamfav.infra.network.models.ApiName
import com.groupe5.steamfav.infra.network.models.GameDetails

class SteamStoreNetwork() : SteamStoreDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val api: RetrofitSteamStoreWebApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_STORE_FRONT)
            .create(RetrofitSteamStoreWebApi::class.java)

    override suspend fun getGameDetails(gameId: Long): GameDetails? =
        api.getGameDetails(gameId).body()


}