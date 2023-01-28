package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.models.SearchItem
import retrofit2.Response

interface SteamStoreDataSource {
    suspend fun getGameDetails(gameId: Long): Response<GameDetails>
    suspend fun search(searchQuery:String): Response<List<SearchItem>>
    suspend fun getGamePublishers(gameId:Long):Response<List<String>>
}