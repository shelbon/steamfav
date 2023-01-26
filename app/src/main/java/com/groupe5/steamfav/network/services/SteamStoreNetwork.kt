package com.groupe5.steamfav.network.services

import com.groupe5.steamfav.network.abstraction.RetrofitSteamStore
import com.groupe5.steamfav.network.abstraction.RetrofitSteamStoreWebApi
import com.groupe5.steamfav.network.abstraction.SteamStoreDataSource
import com.groupe5.steamfav.network.models.ApiName
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.models.SearchItem
import com.groupe5.steamfav.network.response.adapter.GameDetailAdapter
import retrofit2.Response

class SteamStoreNetwork() : SteamStoreDataSource {
    private val networkConfigurator: NetworkConfigurator = NetworkConfigurator()
    private val steamStoreWebApi: RetrofitSteamStoreWebApi =
        networkConfigurator.provideRetrofit(ApiName.STEAM_STORE_FRONT, listOf(GameDetailAdapter()))
            .create(RetrofitSteamStoreWebApi::class.java)
    private val steamStore:RetrofitSteamStore=networkConfigurator.provideRetrofit(ApiName.STEAM_STORE, listOf(GameDetailAdapter()))
        .create(RetrofitSteamStore::class.java)

    override suspend fun getGameDetails(gameId: Long): Response<GameDetails> =
        steamStoreWebApi.getGameDetails(gameId);

    override suspend fun search(searchQuery: String): Response<List<SearchItem>> =
        steamStore.search(searchQuery)

    override suspend fun getGamePublishers(gameId: Long): Response<List<String>> =
        steamStoreWebApi.getGamePublishers(gameId)



}