package com.jhkim.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkVClipResource(
    @SerialName("meta") val meta: VClipMeta,
    @SerialName("documents") val documents: List<VClipDocument>
)

@Serializable
data class VClipMeta(
    @SerialName("total_count") val totalCount: Int = 0,
    @SerialName("pageable_count") val pageableCont: Int = 0,
    @SerialName("is_end") val isEnd: Boolean = false
)

@Serializable
data class VClipDocument(
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("datetime") val datetime: String,
    @SerialName("play_time") val playTime: Int,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("author") val author: String,
)