package com.groupe5.steamfav.data

import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.network.services.SteamWorksWebNetwork
import com.groupe5.steamfav.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GamesRepository(
    private val steamWorksNetwork: SteamWorksWebNetwork,
    private val steamStoreWebNetwork: SteamStoreNetwork,
) : GamesRepository {
    override fun getGames(): Flow<Resource<List<GameDetails>>> =
        flow {
            val list =
                steamWorksNetwork.getMostPlayedGames()?.response?.ranks?.mapNotNull { rank ->
                    steamStoreWebNetwork.getGameDetails(rank.appid)
                }
            emit(Resource.success(list))
        }


    override suspend fun getGame(gameId: Long): Flow<GameDetails?> {
        TODO("Not yet implemented")
    }

    override fun getSpotlightGame(): Flow<Resource<GameDetails?>> =
        flow {
            val topReleasedGamesIdOfThese3months =
                steamWorksNetwork.getTopReleaseGamesOfThe3Months()!!
            val retryMax = 3
            var retriedAttempt = 0
            var gameId: Long = 0
            var isGameFound = false;
            while (retriedAttempt <= retryMax && !isGameFound) {
                gameId = topReleasedGamesIdOfThese3months.random()
                val gameDetails = steamStoreWebNetwork.getGameDetails(gameId)
                if (gameDetails?.type?.lowercase() != "game") {
                    if (retriedAttempt == retryMax) {
                        isGameFound = false
                    } else {
                        retriedAttempt++
                    }
                    continue
                }
                emit(Resource.success(gameDetails))
                isGameFound = true
            }
            if (retriedAttempt > 0 && !isGameFound) {
                emit(Resource.error("No games found", null))
            }
        }

}