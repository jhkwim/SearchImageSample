package com.jhkim.core.network

import com.jhkim.core.network.model.NetworkImageResource
import com.jhkim.core.network.model.NetworkVClipResource

interface ImageNetworkDataSource {

    suspend fun getImageResource(query: String): NetworkImageResource

    suspend fun getVClipResource(query: String): NetworkVClipResource
}