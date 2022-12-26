package com.groupe5.steamfav.infra.network.abstraction

import com.groupe5.steamfav.infra.network.response.MostPlayedGames

interface SteamWorksWebDataSource {
    suspend fun getMostPlayedGames(): MostPlayedGames?
}
