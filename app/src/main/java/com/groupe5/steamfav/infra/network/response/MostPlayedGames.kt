package com.groupe5.steamfav.infra.network.response

import com.squareup.moshi.Json


data class MostPlayedGames(
    val response: Response
) {
    data class Response(
        @Json(name = "rollup_date")
        val rollupDate: Long,
        @Json(name = "ranks")
        val ranks: List<Rank>
    ) {
        data class Rank(
            @Json(name = "rank")
            val rank: Long,
            @Json(name = "appid")
            val appid: Long,
            @Json(name = "last_week_rank")
            val lastWeekRank: Long,
            @Json(name = "peak_in_game")
            val peakInGame: Long
        )
    }
}



