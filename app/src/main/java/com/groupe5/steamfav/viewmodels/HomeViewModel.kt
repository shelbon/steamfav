package com.groupe5.steamfav.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.utils.Resource


import kotlinx.coroutines.flow.onStart

class HomeViewModel(private val gamesRepository: GamesRepository) : ViewModel() {
    val games: LiveData<Resource<List<GameDetails>>>
        get() =
            gamesRepository.getGames().onStart {
                emit(Resource.loading())
            }.asLiveData()
    val spotLightGame: LiveData<Resource<GameDetails?>>
        get() = gamesRepository.getSpotlightGame().onStart {
            emit(Resource.loading())
        }.asLiveData()
}