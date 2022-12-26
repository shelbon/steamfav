package com.groupe5.steamfav.infra.network.abstraction

import com.groupe5.steamfav.infra.network.models.GameDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitSteamStoreWebApi {
    @GET("/api/appdetails")
    suspend fun getGameDetails(@Query("appids") gameId: Long): Response<GameDetails>

}