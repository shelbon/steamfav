package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.GameDetails
import retrofit2.Response

interface SteamStoreDataSource {
    suspend fun getGameDetails(gameId: Long): Response<GameDetails>
}