package com.groupe5.steamfav.network.response

import com.squareup.moshi.Json


typealias GameDetailPublishersResponse=Map<String,PublishersData>

data class PublishersData(
    @Json(name = "data")
    val data: List<String>
)