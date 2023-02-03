package com.groupe5.steamfav.data.impl

import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.groupe5.steamfav.data.abstraction.LikedGamesRepository
import com.groupe5.steamfav.data.models.LikedGame
import com.groupe5.steamfav.data.utils.CollectionNames
import com.groupe5.steamfav.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class LikedGamesRepository(private val firebaseStore: FirebaseFirestore) : LikedGamesRepository {
    private var collectionName: String = CollectionNames.LIKED_GAMES.trueName

    override fun addLikedGame(likedGame: LikedGame) = flow {
        emit(NetworkResult.Loading())
        val likedGameRef =
            firebaseStore.collection(collectionName).add(likedGame).await()
        emit(NetworkResult.Success(likedGameRef))
    }.flowOn(Dispatchers.IO).catch {
        emit(NetworkResult.Error(it.localizedMessage ?: "Something went wrong"))
    }


    override fun addUserToLikedGame(likedGameId: Long, gameName: String, userId: String) = flow {
        val existLikedGame = exist(likedGameId).first()

        if (existLikedGame) {
            val snapshot =
                firebaseStore.collection(collectionName)
                    .whereEqualTo("appid", likedGameId)
                    .snapshots()
            val task = snapshot.first()
            if (task.documents.isNotEmpty()) {
                val document = task.documents[0]?.toObject<LikedGame>()
                document?.let { likedGame ->
                    val newUsers = mutableListOf<String>()
                    newUsers.addAll(likedGame.users)
                    newUsers.add(userId)

                    firebaseStore.collection(collectionName).document(task.documents[0].id)
                        .update("users", newUsers).await()
                    emit(NetworkResult.Success(true))
                }
            }


        } else {
            val likedGame = LikedGame(likedGameId, gameName, listOf(userId))
            val result = firebaseStore.collection(collectionName).add(likedGame).await()
            val doc = result.get().await()
            if (!doc.exists()) {
                emit(
                    NetworkResult.Error(
                        "game could not be added to your liked list", false
                    )
                )
            } else {
                emit(NetworkResult.Success(true))
            }

        }

    }.flowOn(Dispatchers.IO)

    override fun removeLikedGame(likedGameId: Long, userId: String): Flow<NetworkResult<Int>> =
        flow {
            val snapshot =
                firebaseStore.collection(collectionName.toString())
                    .whereEqualTo("appid", likedGameId)
                    .whereArrayContains("users", userId).snapshots()
            val documents = snapshot.first()
            documents.documents.let { documentsSnapshot ->
                if (documentsSnapshot.isNotEmpty()) {
                    val likedGame = documentsSnapshot[0].toObject<LikedGame>()
                    likedGame?.let { likedGame ->
                        val filteredUsers = likedGame.users.filter { uuid ->
                            uuid != userId
                        }
                        firebaseStore.collection(collectionName).document(documents.documents[0].id)
                            .update("users", filteredUsers).await()
                        val gameExistForUser = existForUser(userId, likedGameId).first()
                        if (gameExistForUser) {
                            emit(
                                NetworkResult.Error(
                                    "game could not be removed to your liked list", 0
                                )
                            )
                        } else {
                            emit(NetworkResult.Success(1))
                        }
                    }

                } else {
                    emit(
                        NetworkResult.Error(
                            "not-found",
                            0
                        )
                    )
                }


            }
        }.flowOn(Dispatchers.IO)

    override fun existForUser(userId: String, likedGameId: Long): Flow<Boolean> = flow {
        val snapshot = firebaseStore.collection(collectionName)
            .whereEqualTo("appid", likedGameId)
            .whereArrayContains("users", userId)
            .count()
            .get(AggregateSource.SERVER)
            .await()

        emit(snapshot.count > 0)

    }.flowOn(Dispatchers.IO)

    override fun exist(gameId: Long): Flow<Boolean> = flow {
        val snapshot = firebaseStore.collection(collectionName)
            .whereEqualTo("appid", gameId)
            .count()
            .get(AggregateSource.SERVER).await()

        emit(snapshot.count > 0)
    }.flowOn(Dispatchers.IO)
}