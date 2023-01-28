package com.groupe5.steamfav.network.services

import com.groupe5.steamfav.network.abstraction.RetrofitSteamWorksWebApi
import com.groupe5.steamfav.network.abstraction.SteamWorksWebDataSource
import com.groupe5.steamfav.network.models.ApiName
import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.MostPlayedGames
import com.groupe5.steamfav.network.response.adapter.TopReleasePageAdapter
import retrofit2.Response

class SteamWorksWebNetwork() : SteamWorksWebDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val api: RetrofitSteamWorksWebApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_WORKS, listOf(TopReleasePageAdapter()))
            .create(RetrofitSteamWorksWebApi::class.java)


    override suspend fun getMostPlayedGames(): Response<MostPlayedGames> {
        return api.getMostPlayedGames()
    }

    override suspend fun getTopReleaseGamesOfThe3Months(): Response<TopReleasePage> {
        return api.getTopReleasesPages()
    }


}