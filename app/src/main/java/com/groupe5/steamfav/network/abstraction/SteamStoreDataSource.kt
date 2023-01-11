package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.GameDetails

interface SteamStoreDataSource {
    suspend fun getGameDetails(gameId: Long): GameDetails?
}