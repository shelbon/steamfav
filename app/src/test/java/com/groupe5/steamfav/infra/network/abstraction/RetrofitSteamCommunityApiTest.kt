package com.groupe5.steamfav.infra.network.abstraction

import HttpTest
import com.groupe5.steamfav.network.abstraction.RetrofitSteamCommunityApi
import com.groupe5.steamfav.network.models.UserInfo
import com.groupe5.steamfav.network.response.adapter.UserInfoAdapter
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
class RetrofitSteamCommunityApiTest:HttpTest() {
    private lateinit var mockRetrofit: RetrofitSteamCommunityApi
    @BeforeEach
    override fun setUp() {
        super.setUp()
        val userInfoAdapter= UserInfoAdapter()
        val moshi = Moshi.Builder()
            .add(userInfoAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()
        mockRetrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(server.url(""))
            .client(okHttpClient)
            .build().create(RetrofitSteamCommunityApi::class.java)
    }
    @Test
    fun `should return empty list when usersInfo not found`()=  runTest(this.testDispatcher) {
        enqueueMockResponse("userInfo_no_data.json")
        val expectResult= emptyList<UserInfo>()
        val input="10,12,13"
        val usersInfo = mockRetrofit.resolveUsers(input).body()
        val request = server.takeRequest()
        Assertions.assertThat(request.path).isEqualTo("/actions/ajaxresolveusers?steamids=10,12,13")
        Assertions.assertThat(usersInfo).isNotNull
        Assertions.assertThat(usersInfo).isEqualTo(expectResult)


    }  @Test
    fun `should return data when usersInfos found`()=  runTest(this.testDispatcher) {
            enqueueMockResponse("userInfo.json")
            val expectResult= listOf<UserInfo>(UserInfo(
                76561198042965266,
                "Revadike"
                ),UserInfo(
                76561198034957967,
                "MalikQayum"
            ))
            val input="76561198042965266,76561198034957967"
            val usersInfo = mockRetrofit.resolveUsers(input).body()
            val request = server.takeRequest()
            Assertions.assertThat(request.path).isEqualTo("/actions/ajaxresolveusers?steamids=76561198042965266,76561198034957967")
            Assertions.assertThat(usersInfo).isNotNull
            Assertions.assertThat(usersInfo).isEqualTo(expectResult)


        }

}