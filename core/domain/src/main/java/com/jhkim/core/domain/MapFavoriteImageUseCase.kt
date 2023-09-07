package com.jhkim.core.domain

import com.jhkim.core.data.repository.FavoriteImageRepository
import com.jhkim.core.model.ImageResource
import com.jhkim.core.model.ImageWithFavoriteStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MapFavoriteImageUseCase @Inject constructor(
    private val favoriteImageRepository: FavoriteImageRepository
) {

    operator fun invoke(imageResources: List<ImageResource>): Flow<List<ImageWithFavoriteStatus>> =
        favoriteImageRepository.favoriteImages.map { favoriteImages ->
            imageResources.map {
                ImageWithFavoriteStatus(
                    imageResource = it,
                    isFavorite = it in favoriteImages
                )
            }
        }


}