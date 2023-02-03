package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitSteamCommunityApi {
    @GET("/actions/ajaxresolveusers")
   suspend fun resolveUser(@Query("steamids") steamid: Long): Response<List<UserInfo>>
    @GET("/actions/ajaxresolveusers")
    suspend fun resolveUsers(@Query("steamids",encoded = true) steamids:String):  Response<List<UserInfo>>
}