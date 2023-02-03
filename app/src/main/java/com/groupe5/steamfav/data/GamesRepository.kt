package com.groupe5.steamfav.data

import android.content.res.Resources
import com.groupe5.steamfav.R
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.models.SearchItem
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.network.services.SteamWorksWebNetwork
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GamesRepository(
    private val steamWorksNetwork: SteamWorksWebNetwork,
    private val steamStoreWebNetwork: SteamStoreNetwork,
) : GamesRepository {
    override fun getGames(): Flow<NetworkResult<List<GameDetails>>> =
        flow {
            val response = steamWorksNetwork.getMostPlayedGames()
            var list: List<GameDetails>? = null
            if (response.isSuccessful) {
                list = response.body()?.response?.ranks?.mapNotNull { rank ->
                    val gameDetailsResponse = steamStoreWebNetwork.getGameDetails(rank.appid)
                    if (gameDetailsResponse.isSuccessful) {
                        gameDetailsResponse.body()
                    } else {
                        when (response.code()) {
                            429 -> emit(
                                NetworkResult.Error(
                                    Resources.getSystem()
                                        .getString(R.string.error_too_many_requests), null
                                )
                            )
                            200 ->
                                gameDetailsResponse.body()

                            else -> {
                                val errorMsg = gameDetailsResponse.errorBody()?.string()!!
                                if (errorMsg == "null") {
                                    emit(
                                        NetworkResult.Error(
                                            Resources.getSystem()
                                                .getString(R.string.error_not_found_game), null
                                        )
                                    )
                                } else {
                                    emit(NetworkResult.Error(errorMsg, null))
                                }
                            }
                        }

                        null
                    }
                }
                emit(NetworkResult.Success(list))

            }
            if (list.isNullOrEmpty()) {
                emit(NetworkResult.Error("games not found", null))
            }

        }.flowOn(Dispatchers.IO).catch {
            emit(NetworkResult.Error(it.localizedMessage ?: "an error occurred", null))
        }


    override suspend fun getGame(gameId: Long): Flow<NetworkResult<GameDetails?>> =
        flow {
            val gameDetailsResponse = steamStoreWebNetwork.getGameDetails(gameId);
            if (gameDetailsResponse.isSuccessful) {
                emit(NetworkResult.Success(gameDetailsResponse.body()))
            } else {
                when (gameDetailsResponse.code()) {
                    429 -> emit(
                        NetworkResult.Error(
                            Resources.getSystem()
                                .getString(R.string.error_too_many_requests), null
                        )
                    )
                    200 ->
                        gameDetailsResponse.body()
                    else -> {
                        val errorMsg = gameDetailsResponse.errorBody()?.string()!!
                        if (errorMsg == "null") {
                            emit(
                                NetworkResult.Error(
                                    Resources.getSystem()
                                        .getString(R.string.error_not_found_game), null
                                )
                            )
                        } else {
                            emit(NetworkResult.Error(errorMsg, null))
                        }

                    }
                }
            }

        }.flowOn(Dispatchers.IO).catch {
            emit(NetworkResult.Error(it.localizedMessage ?: "an error occurred", null))
        }

    override fun getSpotlightGame(): Flow<NetworkResult<GameDetails?>> =
        flow {
            val topReleasedGamesRequest =
                steamWorksNetwork.getTopReleaseGamesOfThe3Months()
            if (topReleasedGamesRequest.isSuccessful) {
                val topReleasedGamesIdOfThese3months =
                    topReleasedGamesRequest.body()!!
                val retryMax = 3
                var retriedAttempt = 0
                var gameId: Long = 0
                var isGameFound = false
                while (retriedAttempt <= retryMax && !isGameFound) {
                    gameId = topReleasedGamesIdOfThese3months.random()
                    val gameDetailsRequest = steamStoreWebNetwork.getGameDetails(gameId)
                    if (gameDetailsRequest.isSuccessful) {
                        val gameDetails = gameDetailsRequest.body()
                        if (gameDetails?.type?.lowercase() != "game") {
                            if (retriedAttempt == retryMax) {
                                isGameFound = false
                            } else {
                                retriedAttempt++
                            }
                            continue
                        }
                        emit(NetworkResult.Success(gameDetails))
                        isGameFound = true
                    }

                }
                if (retriedAttempt > 0 && !isGameFound) {
                    emit(NetworkResult.Error("No games found", null))
                }
            }


        }.flowOn(Dispatchers.IO)
            .catch {
                emit(NetworkResult.Error(it.localizedMessage ?: "an error occurred", null))
            }


    override fun search(searchQuery: String): Flow<NetworkResult<List<SearchItem>>> =
        flow {
            val searchResult = steamStoreWebNetwork.search(searchQuery)
            if (searchResult.isSuccessful) {
                emit(NetworkResult.Success(searchResult.body()))
            } else {
                when (searchResult.code()) {
                    429 -> emit(
                        NetworkResult.Error(
                            Resources.getSystem()
                                .getString(R.string.error_too_many_requests), null
                        )
                    )
                    200 ->
                        searchResult.body()
                    else -> {
                        val errorMsg = searchResult.errorBody()?.string()!!
                        emit(NetworkResult.Error(errorMsg, null))

                    }
                }
            }
        }.flowOn(Dispatchers.IO)
            .catch {
                emit(NetworkResult.Error(it.localizedMessage ?: "an error occurred", null))
            }




}