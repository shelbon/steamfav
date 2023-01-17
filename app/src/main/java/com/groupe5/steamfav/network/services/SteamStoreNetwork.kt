package com.groupe5.steamfav.network.services

import com.groupe5.steamfav.network.abstraction.RetrofitSteamStoreWebApi
import com.groupe5.steamfav.network.abstraction.SteamStoreDataSource
import com.groupe5.steamfav.network.models.ApiName
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.response.adapter.GameDetailAdapter
import retrofit2.Response

class SteamStoreNetwork() : SteamStoreDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val api: RetrofitSteamStoreWebApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_STORE_FRONT, GameDetailAdapter())
            .create(RetrofitSteamStoreWebApi::class.java)

    override suspend fun getGameDetails(gameId: Long): Response<GameDetails> =
        api.getGameDetails(gameId);


}