package com.groupe5.steamfav.infra.network.abstraction

import com.groupe5.steamfav.infra.network.models.GameDetails

interface SteamStoreDataSource {
    suspend fun getGameDetails(gameId: Long): GameDetails?
}