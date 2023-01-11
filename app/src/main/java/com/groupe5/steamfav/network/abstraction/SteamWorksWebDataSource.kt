package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.MostPlayedGames

interface SteamWorksWebDataSource {
    suspend fun getMostPlayedGames(): MostPlayedGames?
    suspend fun getTopReleaseGamesOfThe3Months(): TopReleasePage?
}
