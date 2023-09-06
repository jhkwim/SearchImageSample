package com.jhkim.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jhkim.core.network.BuildConfig
import com.jhkim.core.network.ImageNetworkDataSource
import com.jhkim.core.network.model.NetworkImageResource
import com.jhkim.core.network.model.NetworkVClipResource
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface KaKaoDeveloperApi {

    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
//        sort: String,
//        page: Int,
//        size: Int
    ): NetworkImageResource

    @GET("v2/search/vclip")
    suspend fun searchVClip(
        @Query("query") query: String,
//        sort: String,
//        page: Int,
//        size: Int
    ): NetworkVClipResource

}

private const val BASE_URL = BuildConfig.API_URL

@Singleton
class RetrofitImageNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory
) : ImageNetworkDataSource {

    private val kaKaoDeveloperApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(KaKaoDeveloperApi::class.java)

    override suspend fun getImageResource(query: String): NetworkImageResource =
        kaKaoDeveloperApi.searchImage(query = query)

    override suspend fun getVClipResource(query: String): NetworkVClipResource =
        kaKaoDeveloperApi.searchVClip(query = query)

}