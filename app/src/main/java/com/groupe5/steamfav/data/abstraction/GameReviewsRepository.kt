package com.groupe5.steamfav.data.abstraction

import com.groupe5.steamfav.domain.Review
import com.groupe5.steamfav.network.models.UserInfo
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface GameReviewsRepository {
    fun reviews(gameId: Long): Flow<NetworkResult<List<Review>>>
    fun usersInfo(ids:List<Long>): Flow<NetworkResult<List<UserInfo>>>
}