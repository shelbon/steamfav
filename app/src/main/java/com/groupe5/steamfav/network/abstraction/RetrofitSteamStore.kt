package com.groupe5.steamfav.network.abstraction;

import com.groupe5.steamfav.network.models.SearchItem;


import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.Locale


interface RetrofitSteamStore {

    @GET("/search/suggest?realm=1&origin=https:%2F%2Fstore.steampowered.com&f=jsonfull&require_type=game")
    suspend fun search(@Query("term")term: String,
                       @Query("l")language:String= Locale
                                .getDefault()
                                .getDisplayLanguage(Locale.ENGLISH)
                                .lowercase(),
                                @Query("cc")countryCode:String=Locale
                                     .getDefault()
                                     .country):Response<List<SearchItem>>
}
