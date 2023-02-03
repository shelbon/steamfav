package com.groupe5.steamfav.data.abstraction

import com.google.firebase.firestore.DocumentReference
import com.groupe5.steamfav.data.models.LikedGame
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LikedGamesRepository {
    fun addLikedGame(likedGame: LikedGame): Flow<NetworkResult<DocumentReference>>
    fun addUserToLikedGame(likedGameId:Long,gameName: String, userId: String): Flow<NetworkResult<Boolean>>
    fun removeLikedGame(likedGameId:Long, userId: String): Flow<NetworkResult<Int>>
    fun existForUser(userId: String, likedGameId:Long): Flow<Boolean>
    fun exist(gameId:Long):Flow<Boolean>
}