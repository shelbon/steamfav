package com.groupe5.steamfav.network.response

import com.squareup.moshi.Json

data class TopReleasePageResponse(
    val response: Response,
)

data class Response(
    val pages: List<Page>
)

data class Page(
    val name: String,

    @Json(name = "start_of_month")
    val startOfMonth: Long,

    @Json(name = "url_path")
    val urlPath: String,

    @Json(name = "item_ids")
    val itemIDS: List<ItemID>
)

data class ItemID(
    val appid: Long
)