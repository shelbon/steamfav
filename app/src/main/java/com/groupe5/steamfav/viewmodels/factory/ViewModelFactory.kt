package com.groupe5.steamfav.viewmodels.factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.groupe5.steamfav.data.abstraction.GameReviewsRepository
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.viewmodels.GameDetailsViewModel
import com.groupe5.steamfav.viewmodels.HomeViewModel
import com.groupe5.steamfav.viewmodels.SearchViewModel

class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: GamesRepository,
    private val repository2:GameReviewsRepository?,
    defaultArgs: Bundle? = null
) :
    AbstractSavedStateViewModelFactory(owner, defaultArgs) {


    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository, handle) as T
        } else if (modelClass.isAssignableFrom(GameDetailsViewModel::class.java)) {
            return GameDetailsViewModel(repository,repository2!!, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
