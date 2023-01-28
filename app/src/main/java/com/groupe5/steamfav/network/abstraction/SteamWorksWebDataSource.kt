package com.groupe5.steamfav.network.abstraction

import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.MostPlayedGames
import retrofit2.Response

interface SteamWorksWebDataSource {
    suspend fun getMostPlayedGames(): Response<MostPlayedGames>
    suspend fun getTopReleaseGamesOfThe3Months(): Response<TopReleasePage>
}
