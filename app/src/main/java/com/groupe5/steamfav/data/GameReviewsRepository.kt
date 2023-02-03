package com.groupe5.steamfav.data

import android.content.res.Resources
import com.groupe5.steamfav.R
import com.groupe5.steamfav.data.abstraction.GameReviewsRepository
import com.groupe5.steamfav.domain.Review
import com.groupe5.steamfav.network.models.UserInfo
import com.groupe5.steamfav.network.services.SteamCommunityNetwork
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn



class GameReviewsRepository(
    private val steamCommunityNetwork: SteamCommunityNetwork,
    private val steamStoreWebNetwork: SteamStoreNetwork,
) : GameReviewsRepository {
    override fun reviews(gameId: Long): Flow<NetworkResult<List<Review>>> =
        flow {
            val reviews = steamStoreWebNetwork.getReviews(gameId)

            if (reviews.isSuccessful) {
                emit(NetworkResult.Success(reviews.body()))
            } else {
                when (reviews.code()) {
                    429 -> emit(
                        NetworkResult.Error(
                            Resources.getSystem()
                                .getString(R.string.error_too_many_requests), null
                        )
                    )
                    200 ->
                        reviews.body()
                    else -> {
                        val errorMsg = reviews.errorBody()?.string()!!
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


        }.flowOn(Dispatchers.IO)
            .catch {
                emit(NetworkResult.Error(it.localizedMessage ?: "an error occurred", null))
            }.conflate()

    override fun usersInfo(ids: List<Long>): Flow<NetworkResult<List<UserInfo>>> = flow {
        val usersInfo = steamCommunityNetwork.getUsersInfo(ids)

        if (usersInfo.isSuccessful) {
            emit(NetworkResult.Success(usersInfo.body()))
        } else {
            when (usersInfo.code()) {
                429 -> emit(
                    NetworkResult.Error(
                        Resources.getSystem()
                            .getString(R.string.error_too_many_requests), null
                    )
                )
                200 ->
                    usersInfo.body()
                else -> {
                    val errorMsg = usersInfo.errorBody()?.string()!!
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


    }.flowOn(Dispatchers.IO)
        .catch {
            emit(NetworkResult.Error(it.localizedMessage ?: "an error occurred", null))
        }
}