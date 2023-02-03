package com.groupe5.steamfav.network.services

import com.groupe5.steamfav.network.abstraction.RetrofitSteamCommunityApi
import com.groupe5.steamfav.network.abstraction.SteamCommunityDataSource
import com.groupe5.steamfav.network.models.ApiName
import com.groupe5.steamfav.network.models.UserInfo
import com.groupe5.steamfav.network.response.adapter.UserInfoAdapter
import retrofit2.Response

class SteamCommunityNetwork:SteamCommunityDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val steamCommunityApi: RetrofitSteamCommunityApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_COMMUNITY, listOf(UserInfoAdapter()))
            .create(RetrofitSteamCommunityApi::class.java)
    override suspend fun getUsersInfo(steamIds: List<Long>): Response<List<UserInfo>> =
        steamCommunityApi.resolveUsers(steamIds.joinToString(",") )



    override suspend fun getUserInfo(steamId: Long): Response<List<UserInfo>> =
        steamCommunityApi.resolveUser(steamId)

}