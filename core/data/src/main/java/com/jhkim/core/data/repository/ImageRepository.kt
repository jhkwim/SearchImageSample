package com.jhkim.core.data.repository

import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    val favoriteImages: Flow<List<ImageResource>>

    fun getImageResources(query: String): Flow<List<ImageResource>>

    suspend fun addFavoriteImageResource(imageResource: ImageResource)

    suspend fun removeFavoriteImageResource(imageResource: ImageResource)

}