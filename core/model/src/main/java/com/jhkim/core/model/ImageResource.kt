package com.jhkim.core.model

data class ImageResource(
    val url: String,
    val dateTime: String
)

data class ImageWithFavoriteStatus(
    val imageResource: ImageResource,
    val isFavorite: Boolean
)
