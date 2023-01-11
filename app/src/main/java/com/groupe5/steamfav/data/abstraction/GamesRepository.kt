package com.groupe5.steamfav.data.abstraction

import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGames(): Flow<Resource<List<GameDetails>>>
    suspend fun getGame(gameId: Long): Flow<GameDetails?>
    fun getSpotlightGame(): Flow<Resource<GameDetails?>>
}