package com.jhkim.core.data.repository

import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow

interface FavoriteImageRepository {

    val favoriteImages: Flow<List<ImageResource>>

    suspend fun addFavoriteImageResource(imageResource: ImageResource)

    suspend fun removeFavoriteImageResource(imageResource: ImageResource)
}