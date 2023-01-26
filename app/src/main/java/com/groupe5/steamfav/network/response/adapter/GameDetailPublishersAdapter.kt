package com.groupe5.steamfav.network.response.adapter

import com.groupe5.steamfav.network.abstraction.DeserializeAdapter
import com.groupe5.steamfav.network.response.GameDetailPublishersResponse
import com.squareup.moshi.FromJson

class GameDetailPublishersAdapter : DeserializeAdapter<GameDetailPublishersResponse, List<String>> {
    @FromJson
    override fun from(obj: GameDetailPublishersResponse): List<String> =
        obj.values.let{response->
            response.map{responseData ->
               return responseData.data

            }
        }
}