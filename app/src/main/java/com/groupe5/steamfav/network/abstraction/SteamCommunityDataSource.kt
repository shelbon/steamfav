package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.UserInfo
import retrofit2.Response

interface SteamCommunityDataSource {
    suspend fun getUserInfo(steamId: Long): Response<List<UserInfo>>
    suspend fun getUsersInfo(steamIds: List<Long>): Response<List<UserInfo>>
}