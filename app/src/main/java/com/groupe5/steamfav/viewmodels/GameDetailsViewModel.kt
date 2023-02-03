package com.groupe5.steamfav.viewmodels


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.groupe5.steamfav.data.abstraction.GameReviewsRepository
import com.groupe5.steamfav.data.abstraction.GamesRepository
import com.groupe5.steamfav.domain.Author
import com.groupe5.steamfav.domain.Review
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.utils.NetworkResult
import com.groupe5.steamfav.utils.RefreshableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


@OptIn(ExperimentalCoroutinesApi::class)
class GameDetailsViewModel(
    private val gamesRepository: GamesRepository,
    private val gameReviewsRepository: GameReviewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _gameId: StateFlow<Long> = savedStateHandle.getStateFlow("gameId", 0L)
    private val _description = savedStateHandle.getStateFlow("game_details_description", "")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _getGameLiveData: RefreshableLiveData<NetworkResult<GameDetails?>> =
        RefreshableLiveData {
            _gameId.flatMapLatest { gameId ->

                gamesRepository.getGame(gameId).onStart {
                    emit(NetworkResult.Loading())
                }
            }.asLiveData()
        }
    private val _getGameReviews: RefreshableLiveData<NetworkResult<List<Review>>> =
        RefreshableLiveData {
            _gameId.flatMapLatest { gameId ->

                gameReviewsRepository.reviews(gameId).onStart {
                    emit(NetworkResult.Loading())
                }.map {networkResult->
                    when (networkResult) {
                        is NetworkResult.Success -> {
                            val ids = networkResult.data!!.map { review ->
                                review.author.id
                            }
                            networkResult.copy(ids.let { userIds ->
                                val usersInfo = gameReviewsRepository.usersInfo(userIds).first()
                                usersInfo.data?.map { userInfo ->
                                    val prevIncompleteReview: Review = networkResult.data.filter { review ->
                                        review.author.id == userInfo.id
                                    }[0]
                                    prevIncompleteReview.let { prevReview ->
                                        Review(
                                            prevReview.recommendationId,
                                            Author(
                                                userInfo.id,
                                                userInfo.personName
                                            ),
                                            prevReview.rating,
                                            prevReview.body

                                        )
                                    }

                                } ?: emptyList()

                            })
                        }

                        else -> networkResult
                    }
                }
            }.flowOn(Dispatchers.IO).asLiveData()
        }

    fun gameId(gameId: Long) {
        savedStateHandle["gameId"] = gameId
    }

    fun refreshGameData() {
        _getGameLiveData.refresh()
    }

    fun refreshGameReviews() {
        _getGameReviews.refresh()
    }

    fun setDescription(description: String) {
        savedStateHandle["game_details_description"] = description
    }

    fun description() = _description.value
    fun reviews() = _getGameReviews
    fun game(): RefreshableLiveData<NetworkResult<GameDetails?>> {
        return _getGameLiveData
    }

}