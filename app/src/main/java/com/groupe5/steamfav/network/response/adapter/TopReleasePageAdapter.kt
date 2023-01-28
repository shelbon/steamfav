package com.groupe5.steamfav.network.response.adapter

import com.groupe5.steamfav.network.abstraction.DeserializeAdapter
import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.TopReleasePageResponse
import com.squareup.moshi.FromJson

class TopReleasePageAdapter : DeserializeAdapter<TopReleasePageResponse, TopReleasePage> {
    @FromJson
    override fun from(obj: TopReleasePageResponse): TopReleasePage {
        return obj.response.pages.flatMap { page ->
            page.itemIDS.map { itemId ->
                itemId.appid
            }
        }
    }


}