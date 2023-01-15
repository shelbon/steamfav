package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.GameDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface RetrofitSteamStoreWebApi {
    @GET("/api/appdetails")
    suspend fun getGameDetails(@Query("appids") gameId: Long,@Query("l") language:String= Locale.getDefault().getDisplayLanguage(Locale.ENGLISH).lowercase()): Response<GameDetails>


}