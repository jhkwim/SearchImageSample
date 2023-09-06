package com.jhkim.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImageResource(
    @SerialName("meta") val meta: ImageMeta,
    @SerialName("documents") val documents: List<ImageDocument>
)

@Serializable
data class ImageMeta(
    @SerialName("total_count") val totalCount: Int = 0,
    @SerialName("pageable_count") val pageableCont: Int = 0,
    @SerialName("is_end") val isEnd: Boolean = false
)

@Serializable
data class ImageDocument(
    @SerialName("collection") val collection: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("display_sitename") val displaySiteName: String,
    @SerialName("doc_url") val docUrl: String,
    @SerialName("datetime") val datetime: String,
)