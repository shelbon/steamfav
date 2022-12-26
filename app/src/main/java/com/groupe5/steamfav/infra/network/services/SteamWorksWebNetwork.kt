package com.groupe5.steamfav.infra.network.services

import com.groupe5.steamfav.infra.network.abstraction.RetrofitSteamWorksWebApi
import com.groupe5.steamfav.infra.network.abstraction.SteamWorksWebDataSource
import com.groupe5.steamfav.infra.network.models.ApiName
import com.groupe5.steamfav.infra.network.response.MostPlayedGames

class SteamWorksWebNetwork() : SteamWorksWebDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val api: RetrofitSteamWorksWebApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_WORKS)
            .create(RetrofitSteamWorksWebApi::class.java)


    override suspend fun getMostPlayedGames(): MostPlayedGames? {
        val mostPlayedGamesResponse = api.getMostPlayedGames()
        return mostPlayedGamesResponse.body()
    }


}