package com.groupe5.steamfav.data.abstraction

import com.google.firebase.firestore.DocumentReference
import com.groupe5.steamfav.data.models.LikedGame
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WishlistGameRepository {
    fun addGameToWishList(wishedGame: LikedGame): Flow<NetworkResult<DocumentReference>>
    fun addUserToWishlist(wishedGameId:Long,gameName: String, userId: String): Flow<NetworkResult<Boolean>>
    fun removeGameToWishlist(wishedGameId:Long, userId: String): Flow<NetworkResult<Int>>
    fun existForUser(userId: String, likedGameId:Long): Flow<Boolean>
    fun exist(gameId:Long): Flow<Boolean>
}