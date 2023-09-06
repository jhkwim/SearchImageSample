package com.jhkim.core.network.di

import com.jhkim.core.network.BuildConfig
import com.jhkim.core.network.ImageNetworkDataSource
import com.jhkim.core.network.retrofit.RetrofitImageNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesImageNetworkDataSource(
        networkJson: Json,
        okhttpCallFactory: Call.Factory
    ): ImageNetworkDataSource = RetrofitImageNetwork(networkJson, okhttpCallFactory)

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        if (BuildConfig.DEBUG) {
            prettyPrint = true
        }
    }

    @Provides
    @Singleton
    fun okHttp(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK ${BuildConfig.API_KEY}")
                    .build()
            )
        }
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            }
        )
        .build()

}