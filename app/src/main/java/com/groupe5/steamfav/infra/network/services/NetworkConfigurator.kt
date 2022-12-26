package com.groupe5.steamfav.infra.network.services

import com.groupe5.steamfav.BuildConfig
import com.groupe5.steamfav.BuildConfig.STEAM_STORE_API_BASE_URL
import com.groupe5.steamfav.BuildConfig.STEAM_STORE_BASE_URL
import com.groupe5.steamfav.BuildConfig.STEAM_WORKS_WEB_API_BASE_URL
import com.groupe5.steamfav.infra.network.abstraction.Adapter
import com.groupe5.steamfav.infra.network.models.ApiName
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkConfigurator {
    private fun provideBaseUrl(apiName: ApiName) =
        when (apiName.name) {
            ApiName.STEAM_STORE_FRONT.name -> STEAM_STORE_API_BASE_URL
            ApiName.STEAM_STORE.name -> STEAM_STORE_BASE_URL
            ApiName.STEAM_WORKS.name -> STEAM_WORKS_WEB_API_BASE_URL
            else -> throw IllegalStateException("API NOT FOUND")
        }


    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    fun provideRetrofit(
        apiName: ApiName,
        adapter: Adapter? = null,
        okHttpClient: OkHttpClient = provideOkHttpClient(),
    ): Retrofit {
        val baseUrl = provideBaseUrl(apiName);
        val moshiBuilder = Moshi.Builder()
        moshiBuilder.addLast(KotlinJsonAdapterFactory())
        adapter?.let { nonNullAdapter ->
            moshiBuilder.add(nonNullAdapter)
        }
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }
}