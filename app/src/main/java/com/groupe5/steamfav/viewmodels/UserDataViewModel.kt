package com.groupe5.steamfav.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import com.groupe5.steamfav.data.impl.LikedGamesRepository
import com.groupe5.steamfav.data.impl.WishedGameRepository
import com.groupe5.steamfav.utils.RefreshableLiveData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest


class UserDataViewModel(
    private val likedGamesRepository: LikedGamesRepository,
    private val wishedGameRepository: WishedGameRepository,
    private val firebaseAuth: FirebaseAuth,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _gameId: StateFlow<Long> = savedStateHandle.getStateFlow("gameId", 0L)
    val currentUserHasLikedGame = RefreshableLiveData {
        _gameId.flatMapLatest { id ->

            likedGamesRepository.existForUser(
                firebaseAuth.currentUser?.uid ?: "",
                id
            )

        }.asLiveData()
    }
    val currentUserHasWishlisted = RefreshableLiveData {
        _gameId.flatMapLatest { id ->

            wishedGameRepository.existForUser(
                firebaseAuth.currentUser?.uid ?: "",
                id
            )
        }.asLiveData()
    }

    fun removeLikedGameForUser() =
        likedGamesRepository.removeLikedGame(_gameId.value, firebaseAuth.currentUser?.uid ?: "")
            .asLiveData()

    fun addLikedGameForUser(gameName: String) = _gameId.flatMapLatest { id ->
        likedGamesRepository.addUserToLikedGame(
            id,
            gameName,
            firebaseAuth.currentUser?.uid ?: ""
        )

    }.asLiveData()

    fun removeUserFromWhishlistedGame() = _gameId.flatMapLatest { id ->
        wishedGameRepository
            .removeGameToWishlist(id, firebaseAuth.currentUser?.uid ?: "")
    }
        .asLiveData()

    fun addGameToUserWishlist(gameName: String) = _gameId.flatMapLatest { id ->
        wishedGameRepository.addUserToWishlist(
            _gameId.value,
            gameName,
            firebaseAuth.currentUser?.uid ?: ""
        )
    }
        .asLiveData()

    fun refreshData() {
        savedStateHandle["gameId"] = _gameId.value
        currentUserHasLikedGame.refresh()

    }

    fun refreshWishListed() {
        savedStateHandle["gameId"] = _gameId.value
        currentUserHasWishlisted.refresh()
    }
}