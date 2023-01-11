package com.groupe5.steamfav.network.response

import com.squareup.moshi.Json


typealias GameDetailResponse = Map<String, ResponseData>

data class ResponseData(
    @Json(name = "success")
    val success: Boolean? = false,
    @Json(name = "data")
    val data: Data
)

data class Data(
    @Json(name = "type")
    val type: String,
    @Json(name = "name")
    val name: String,

    @Json(name = "steam_appid")
    val steamAppid: Long,

    @Json(name = "is_free")
    val isFree: Boolean,


    @Json(name = "about_the_game")
    val aboutTheGame: String,
    @Json(name = "short_description")
    val shortDescription: String,
    @Json(name = "header_image")
    val headerImage: String,

    @Json(name = "publishers")
    val publishers: List<String>? = emptyList(),
    @Json(name = "price_overview")
    val priceOverview: PriceOverview? = null,
)

data class PriceOverview(
    val currency: String,
    val initial: Long,
    val final: Long,

    @Json(name = "discount_percent")
    val discountPercent: Long,

    @Json(name = "initial_formatted")
    val initialFormatted: String,

    @Json(name = "final_formatted")
    val finalFormatted: String
)

