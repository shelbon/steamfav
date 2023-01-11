package com.groupe5.steamfav.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.viewmodels.HomeViewModel

class HomeViewModelFactory(private val repository: GamesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
