package com.groupe5.steamfav.network.interceptors




import com.google.common.util.concurrent.RateLimiter
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class RateLimitInterceptor : Interceptor {
    private val limiter = RateLimiter.create(1.0)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        limiter.acquire(1)
        return chain.proceed(chain.request())
    }
}