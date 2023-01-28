package com.groupe5.steamfav.infra.network.abstraction


import HttpTest
import com.groupe5.steamfav.network.response.MostPlayedGames
import com.groupe5.steamfav.network.abstraction.RetrofitSteamWorksWebApi
import com.groupe5.steamfav.network.models.TopReleasePage
import com.groupe5.steamfav.network.response.adapter.TopReleasePageAdapter
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
            .add(TopReleasePageAdapter())
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return  topReleasePage data`() = runTest(testDispatcher) {
        enqueueMockResponse("topReleaseResponse.json")
        val expectedToFound = listOf<Long>(
            1782120, 1063660, 1451090, 1065310, 1515210, 2096600,
            1361210, 1817190, 1205520, 1816300, 2066020, 973230,
            1904540, 1336490, 997010, 1939160, 2096610, 1810580,
            1567020, 1237320, 2119490, 2167580, 1887930, 1717990,
            2155310, 2127790, 2139266, 2139260, 1465750, 2024410,
            529340, 1687950, 1496790, 261550, 1659420, 1549250,
            1789480, 698670, 799600, 1182900, 1588010, 1776380,
            1158160, 1794680, 1974050, 1874490, 1276760, 2064650,
            1324130, 1938090, 1190340, 1997040, 1826140, 1731080,
            2150050, 2133640, 2124750, 2058190, 1337760, 1372110,
            1954200, 1919590, 1657630, 1121640, 1592190, 1401590,
            2060130, 1061910, 1556790, 1816670, 1811260, 1273400,
            745920, 1176470, 962130, 2058180, 1637320, 2087030,
            1869590, 1797880, 1048370, 2115770, 2008400, 1880650,
            2114760, 2012010
        )
        val topReleasePage = mockRetrofit.getTopReleasesPages().body();
        val request = server.takeRequest()
        assertThat(request.path).isEqualTo("/ISteamChartsService/GetTopReleasesPages/v1")
        assertThat(topReleasePage).isNotNull
        assertThat(topReleasePage).isEqualTo(expectedToFound)

    }
}