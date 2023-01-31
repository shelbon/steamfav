package com.groupe5.steamfav.network.response.adapter

import com.groupe5.steamfav.network.abstraction.DeserializeAdapter
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.response.GameDetailResponse
import com.squareup.moshi.FromJson

class GameDetailAdapter : DeserializeAdapter<GameDetailResponse, GameDetails> {
    @FromJson
    override fun from(obj: GameDetailResponse): GameDetails? {
        obj.values.let { responseDatas ->
            responseDatas.forEach { responseData ->
                if (responseData.success == true) {
                    responseData.data.let { gameDetailResponse ->
                        return gameDetailResponse?.steamAppid?.let {steamAppid->
                            GameDetails(
                                steamAppid,
                                gameDetailResponse.name,
                                gameDetailResponse.type,
                                gameDetailResponse.aboutTheGame  ,
                                gameDetailResponse.shortDescription ?:"",
                                gameDetailResponse.headerImage  ,
                                gameDetailResponse.backgroundImage ?:"",
                                gameDetailResponse.backgroundRaw,
                                gameDetailResponse.priceOverview,
                                gameDetailResponse.publishers ?: emptyList(),
                            )
                        }
                    }
                }

            }

        }
        return null

    }

}