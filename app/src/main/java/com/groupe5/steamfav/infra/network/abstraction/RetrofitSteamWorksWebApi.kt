package com.groupe5.steamfav.infra.network.abstraction

import com.groupe5.steamfav.infra.network.response.MostPlayedGames
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitSteamWorksWebApi {
    @GET("/ISteamChartsService/GetMostPlayedGames/v1")
    suspend fun getMostPlayedGames(): Response<MostPlayedGames>
}
