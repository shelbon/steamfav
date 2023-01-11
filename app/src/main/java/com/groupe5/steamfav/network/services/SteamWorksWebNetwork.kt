package com.groupe5.steamfav.network.services

import com.groupe5.steamfav.network.abstraction.RetrofitSteamWorksWebApi
import com.groupe5.steamfav.network.abstraction.SteamWorksWebDataSource
import com.groupe5.steamfav.network.models.ApiName
import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.MostPlayedGames
import com.groupe5.steamfav.network.response.adapter.TopReleasePageAdapter

class SteamWorksWebNetwork() : SteamWorksWebDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val api: RetrofitSteamWorksWebApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_WORKS, TopReleasePageAdapter())
            .create(RetrofitSteamWorksWebApi::class.java)


    override suspend fun getMostPlayedGames(): MostPlayedGames? {
        val mostPlayedGamesResponse = api.getMostPlayedGames()
        return mostPlayedGamesResponse.body()
    }

    override suspend fun getTopReleaseGamesOfThe3Months(): TopReleasePage? {
        return api.getTopReleasesPages().body();
    }


}