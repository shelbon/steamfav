package com.groupe5.steamfav.network.abstraction;

import com.groupe5.steamfav.domain.Review
import com.groupe5.steamfav.network.models.SearchItem;


import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path
import retrofit2.http.Query;
import java.util.Locale


interface RetrofitSteamStore {

    @GET("/search/suggest?realm=1&origin=https:%2F%2Fstore.steampowered.com&f=jsonfull&require_type=game")
    suspend fun search(
        @Query("term") term: String,
        @Query("l") language: String = Locale
            .getDefault()
            .getDisplayLanguage(Locale.ENGLISH)
            .lowercase(),
        @Query("cc") countryCode: String = Locale
            .getDefault()
            .country
    ): Response<List<SearchItem>>

    @GET("/appreviews/{appId}")
    suspend fun reviews(
        @Path("appId") gameId: Long,
        @Query("filter") filter: String = "summary,recent",
        @Query("review_type") reviewType: String = "all",
        @Query("purchase_type") purchaseType: String = "all",
        @Query("playtime_filter_min") playtimeFilterMin: Long = 0,
        @Query("playtime_filter_max") playtimeFilterMax: Long = 0,
        @Query("filter_offtopic_activity") filterOfftopicActivity: Int = 1,
        @Query("l") l: String = Locale
            .getDefault()
            .getDisplayLanguage(Locale.ENGLISH).lowercase(),
        @Query("json") jsonFormat: Int = 1,
        @Query("cursor",encoded = true) cursor: String = "&ast"
    ): Response<List<Review>>
}
