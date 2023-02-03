package com.groupe5.steamfav.network.response

import com.squareup.moshi.Json

data class ReviewResponse (
    val success: Long,

    @Json(name = "query_summary")
    val querySummary: QuerySummary?,

    val reviews: List<Review>?,
    val cursor: String?
)

data class QuerySummary (
    @Json(name = "num_reviews")
    val numReviews: Long,
)

data class Review (
    val recommendationid: String,
    val author: Author,
    val language: String,
    val review: String,

    @Json(name = "timestamp_created")
    val timestampCreated: Long,

    @Json(name = "timestamp_updated")
    val timestampUpdated: Long,

    @Json(name = "voted_up")
    val votedUp: Boolean,

    @Json(name = "votes_up")
    val votesUp: Long,

    @Json(name = "votes_funny")
    val votesFunny: Long,

    @Json(name = "weighted_vote_score")
    val weightedVoteScore: String,

    @Json(name = "comment_count")
    val commentCount: Long,

    @Json(name = "steam_purchase")
    val steamPurchase: Boolean,

    @Json(name = "received_for_free")
    val receivedForFree: Boolean,

    @Json(name = "written_during_early_access")
    val writtenDuringEarlyAccess: Boolean,

    @Json(name = "hidden_in_steam_china")
    val hiddenInSteamChina: Boolean,

    @Json(name = "steam_china_location")
    val steamChinaLocation: String
)

data class Author (
    val steamid: String,

    @Json(name = "num_games_owned")
    val numGamesOwned: Long,

    @Json(name = "num_reviews")
    val numReviews: Long,

    @Json(name = "playtime_forever")
    val playtimeForever: Long,

    @Json(name = "playtime_last_two_weeks")
    val playtimeLastTwoWeeks: Long,

    @Json(name = "playtime_at_review")
    val playtimeAtReview: Long,

    @Json(name = "last_played")
    val lastPlayed: Long
)
