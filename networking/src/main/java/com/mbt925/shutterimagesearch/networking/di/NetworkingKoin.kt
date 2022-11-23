package com.mbt925.shutterimagesearch.networking.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mbt925.shutterimagesearch.networking.interceptors.HeadersInterceptor
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
val networkingModule = module {

    factory<Interceptor> { HeadersInterceptor() }
    single {
        val client = OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl("https://api.shutterstock.com/v2/")
            .client(client)
            .addConverterFactory(get())
            .build()
    }

    single {
        Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    }

}
