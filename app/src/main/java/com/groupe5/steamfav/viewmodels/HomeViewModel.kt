package com.groupe5.steamfav.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.utils.NetworkResult


import kotlinx.coroutines.flow.onStart

class HomeViewModel(private val gamesRepository: GamesRepository) : ViewModel() {
    val games: LiveData<NetworkResult<List<GameDetails>>>
        get() =
            gamesRepository.getGames().onStart {
                emit(NetworkResult.Loading())
            }.asLiveData()
    val spotLightGame: LiveData<NetworkResult<GameDetails?>>
        get() = gamesRepository.getSpotlightGame().onStart {
            emit(NetworkResult.Loading())
        }.asLiveData()
}