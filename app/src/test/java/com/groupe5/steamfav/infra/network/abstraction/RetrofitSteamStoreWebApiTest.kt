package com.groupe5.steamfav.infra.network.abstraction

import HttpTest
import com.groupe5.steamfav.network.abstraction.RetrofitSteamStoreWebApi
import com.groupe5.steamfav.network.models.GameDetails
import com.groupe5.steamfav.network.response.PriceOverview
import com.groupe5.steamfav.network.response.adapter.GameDetailAdapter
import com.groupe5.steamfav.network.response.adapter.GameDetailPublishersAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
internal class RetrofitSteamStoreWebApiTest : HttpTest() {
    private lateinit var mockRetrofit: RetrofitSteamStoreWebApi

    @BeforeEach
    override fun setUp() {
        super.setUp()
        val gameDetailAdapter = GameDetailAdapter()
        val gameDetailPublishersAdapter=GameDetailPublishersAdapter()
        val moshi = Moshi.Builder()
            .add(gameDetailAdapter)
            .add(gameDetailPublishersAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()
        mockRetrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(server.url(""))
            .client(okHttpClient)
            .build().create(RetrofitSteamStoreWebApi::class.java)
    }

    @Test
    fun `should  return null when not data`() = runTest(this.testDispatcher) {
        enqueueMockResponse("freeGameDetailsResponse.json", 404)
        val gameId: Long = 730
        val gameDetails = mockRetrofit
            .getGameDetails(gameId).body()
        val request = server
            .takeRequest()
        Assertions.assertThat(gameDetails)
            .isNull()
        Assertions.assertThat(request.path)
            .isEqualTo("/api/appdetails?appids=$gameId&l=english")
    }

    @Test
    fun `should  return data with no priceOverview for free game `() =
        runTest(this.testDispatcher) {
            enqueueMockResponse("freeGameDetailsResponse.json", 200)
            val expectedGameDetails = GameDetails(
                730,
                "Counter-Strike: Global Offensive",
                "game",
                "Counter-Strike: Global Offensive (CS: GO) expands upon the team-based action gameplay that it pioneered when it was launched 19 years ago.<br />\r\n<br />\r\nCS: GO features new maps, characters, weapons, and game modes, and delivers updated versions of the classic CS content (de_dust2, etc.).<br />\r\n<br />\r\n&quot;Counter-Strike took the gaming industry by surprise when the unlikely MOD became the most played online PC action game in the world almost immediately after its release in August 1999,&quot; said Doug Lombardi at Valve. &quot;For the past 12 years, it has continued to be one of the most-played games in the world, headline competitive gaming tournaments and selling over 25 million units worldwide across the franchise. CS: GO promises to expand on CS' award-winning gameplay and deliver it to gamers on the PC as well as the next gen consoles and the Mac.&quot;",
                "Counter-Strike: Global Offensive (CS: GO) expands upon the team-based action gameplay that it pioneered when it was launched 19 years ago. CS: GO features new maps, characters, weapons, and game modes, and delivers updated versions of the classic CS content (de_dust2, etc.).",
                "https://cdn.akamai.steamstatic.com/steam/apps/730/header.jpg?t=1668125812",
                "https://cdn.akamai.steamstatic.com/steam/apps/730/page_bg_generated_v6b.jpg?t=1668125812",
                null,
                listOf("Valve")
            )
            val gameId: Long = 730
            val gameDetails = mockRetrofit
                .getGameDetails(gameId).body()
            val request = server
                .takeRequest()
            print(request.path)
            Assertions.assertThat(request.path)
                .isEqualTo("/api/appdetails?appids=$gameId&l=english")
            Assertions.assertThat(gameDetails)
                .isNotNull
            Assertions.assertThat(gameDetails).isEqualTo(expectedGameDetails)
        }

    @Test
    fun `should  return data with   priceOverview for paid game `() = runTest(this.testDispatcher) {
        enqueueMockResponse("paidGameDetailsResponse.json", 200)
        val expectedGameDetails = GameDetails(1850570,
            "DEATH STRANDING DIRECTOR'S CUT",
            "game",
            "From legendary game creator Hideo Kojima comes a genre-defying experience, now expanded in this definitive DIRECTOR’S CUT. <br />\r\n <br />\r\nIn the future, a mysterious event known as the Death Stranding has opened a doorway between the living and the dead, leading to grotesque creatures from the afterlife roaming the fallen world marred by a desolate society. <br />\r\n <br />\r\nAs Sam Bridges, your mission is to deliver hope to humanity by connecting the last survivors of a decimated America. <br />\r\n <br />\r\nCan you reunite the shattered world, one step at a time? <br />\r\n <br />\r\nDEATH STRANDING DIRECTOR’S CUT on PC includes HIGH FRAME RATE, PHOTO MODE and ULTRA-WIDE MONITOR SUPPORT. Also includes cross-over content from Valve Corporation’s HALF-LIFE series and CD Projekt Red’s Cyberpunk 2077. Stay connected with players around the globe with the Social Strand System™. <br />\r\n <br />\r\nAll copies of the game will also additionally include: <br />\r\n• “Selections From ‘The Art of DEATH STRANDING’” Digital Book (by Titan Books) <br />\r\n• Backpack Patches <br />\r\n• Bridges Special Delivery Team Suit (Gold) <br />\r\n• BB pod customization (Chiral Gold) <br />\r\n• Power Gloves (Gold) <br />\r\n• Bridges Special Delivery Team Suit (Silver) <br />\r\n• BB pod customization (Omnireflector) <br />\r\n• Power Gloves (Silver)",
            "From legendary game creator Hideo Kojima comes a genre-defying experience, now expanded in this definitive DIRECTOR’S CUT. As Sam Bridges, your mission is to deliver hope to humanity by connecting the last survivors of a decimated America. Can you reunite the shattered world, one step at a time?",
            "https://cdn.akamai.steamstatic.com/steam/apps/1850570/header.jpg?t=1649438096",
            "https://cdn.akamai.steamstatic.com/steam/apps/1850570/page_bg_generated_v6b.jpg?t=1649438096",
            PriceOverview("USD", 3999, 2399, 40, "$39.99", "$23.99"),
            listOf("505 Games")
        )
        val gameId: Long = 1850570
        val gameDetails = mockRetrofit
            .getGameDetails(gameId).body()
        val request = server
            .takeRequest()
        print(request.path)
        Assertions.assertThat(request.path)
            .isEqualTo("/api/appdetails?appids=$gameId&l=english")
        Assertions.assertThat(gameDetails)
            .isNotNull
        Assertions.assertThat(gameDetails).isEqualTo(expectedGameDetails)
    }
    @Test
    fun `should  return publishers`() = runTest(this.testDispatcher) {
        enqueueMockResponse("paidGameDetailsResponse.json", 200)
        val expectedPublishers = listOf("505 Games")
        val gameId: Long = 1850570
        val gameDetails = mockRetrofit
            .getGamePublishers(gameId).body()
        val request = server
            .takeRequest()
        print(request.path)
        Assertions.assertThat(request.path)
            .isEqualTo("/api/appdetails?filters=publishers&appids=$gameId")
        Assertions.assertThat(expectedPublishers)
            .isNotEmpty
        Assertions.assertThat(gameDetails).isEqualTo(expectedPublishers)
    }
}