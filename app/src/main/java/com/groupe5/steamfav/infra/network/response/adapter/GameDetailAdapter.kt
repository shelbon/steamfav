package com.groupe5.steamfav.infra.network.response.adapter

import com.groupe5.steamfav.infra.network.abstraction.DeserializeAdapter
import com.groupe5.steamfav.infra.network.models.GameDetails
import com.groupe5.steamfav.infra.network.response.GameDetailResponse
import com.squareup.moshi.FromJson

class GameDetailAdapter : DeserializeAdapter<GameDetailResponse, GameDetails> {
    @FromJson
    override fun from(obj: GameDetailResponse): GameDetails? {
        obj?.values.let { responseDatas ->
            responseDatas?.forEach { responseData ->
                if (responseData.success == true) {
                    responseData.data.let { gameDetailResponse ->
                        return GameDetails(
                            gameDetailResponse.name,
                            gameDetailResponse.aboutTheGame,
                            gameDetailResponse.priceOverview,
                            gameDetailResponse.publishers ?: emptyList(),
                        )
                    }
                }

            }

        }
        return null

    }

}