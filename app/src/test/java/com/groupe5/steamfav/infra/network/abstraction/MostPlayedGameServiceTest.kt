package com.groupe5.steamfav.infra.network.abstraction


import HttpTest
import com.groupe5.steamfav.infra.network.response.MostPlayedGames
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@OptIn(ExperimentalCoroutinesApi::class)
internal class RetrofitSteamWorksWebApiTest : HttpTest() {


    private lateinit var mockRetrofit: RetrofitSteamWorksWebApi


    @BeforeEach
    override fun setUp() {
        super.setUp()
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        mockRetrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(server.url(""))
            .client(okHttpClient)
            .build().create(RetrofitSteamWorksWebApi::class.java)
    }


    @Test
    fun `should return  null when data was not found`() = runTest(testDispatcher) {
        enqueueMockResponse("mostPlayGamesResponse.json", 404)
        val mostPlayedGame = mockRetrofit
            .getMostPlayedGames()
            .body();
        val request = server
            .takeRequest()
        assertThat(mostPlayedGame)
            .isNull()
        assertThat(request.path)
            .isEqualTo("/ISteamChartsService/GetMostPlayedGames/v1")

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return  data`() = runTest(testDispatcher) {
        enqueueMockResponse("mostPlayGamesResponse.json")
        val expectedToFound = MostPlayedGames.Response.Rank(1, 730, 1, 980649)

        val mostPlayedGame = mockRetrofit.getMostPlayedGames().body();
        val request = server.takeRequest()
        assertThat(request.path).isEqualTo("/ISteamChartsService/GetMostPlayedGames/v1")
        assertThat(mostPlayedGame).isNotNull
        assertThat(mostPlayedGame!!.response.ranks).contains(expectedToFound)

    }

}