package com.groupe5.steamfav.data.abstraction

import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.models.SearchItem
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGames(): Flow<NetworkResult<List<GameDetails>>>
    suspend fun getGame(gameId: Long): Flow<NetworkResult<GameDetails?>>
    fun getSpotlightGame(): Flow<NetworkResult<GameDetails?>>
    fun search(searchQuery: String):Flow<NetworkResult<List<SearchItem>>>
}