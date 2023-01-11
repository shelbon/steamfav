package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.MostPlayedGames
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitSteamWorksWebApi {
    @GET("/ISteamChartsService/GetMostPlayedGames/v1")
    suspend fun getMostPlayedGames(): Response<MostPlayedGames>

    @GET("/ISteamChartsService/GetTopReleasesPages/v1")
    suspend fun getTopReleasesPages(): Response<TopReleasePage?>
}
