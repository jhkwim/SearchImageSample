package com.jhkim.core.data.repository

import com.jhkim.core.datastore.FavoriteImageDataStore
import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteImageRepositoryImpl @Inject constructor(
    private val favoriteImageDataStore: FavoriteImageDataStore
) : FavoriteImageRepository {

    override val favoriteImages: Flow<List<ImageResource>> = favoriteImageDataStore.imageResources

    override suspend fun addFavoriteImageResource(imageResource: ImageResource) =
        favoriteImageDataStore.addFavoriteImage(imageResource)

    override suspend fun removeFavoriteImageResource(imageResource: ImageResource) =
        favoriteImageDataStore.removeFavoriteImage(imageResource)

}