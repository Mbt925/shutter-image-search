package com.mbt925.shutterimagesearch.networking.interceptors

import com.mbt925.shutterimagesearch.networking.BuildConfig
import java.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Basic ${BuildConfig.API_TOKEN}")
            .build()

        return chain.proceed(request)
    }
}
