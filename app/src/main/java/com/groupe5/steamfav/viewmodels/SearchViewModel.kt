package com.groupe5.steamfav.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.network.models.SearchItem
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.utils.RefreshableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

class SearchViewModel(
    private val gamesRepository: GamesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchQuery: StateFlow<String> =  savedStateHandle.getStateFlow("searchQuery", "")
    @OptIn(ExperimentalCoroutinesApi::class)
   private val _searchLiveData: RefreshableLiveData<NetworkResult<List<SearchItem>>> =
        RefreshableLiveData {
            _searchQuery.flatMapLatest { query ->

                gamesRepository.search(query).onStart {
                    emit(NetworkResult.Loading())
                }

            }.asLiveData()
        }

    fun searchQuery(query: String) {
        savedStateHandle["searchQuery"] = query
    }

    fun refreshData() {
        _searchLiveData.refresh()
    }

    fun search(): RefreshableLiveData<NetworkResult<List<SearchItem>>> {
        return _searchLiveData
    }


}