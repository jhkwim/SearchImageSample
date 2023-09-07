package com.jhkim.core.domain

import com.jhkim.core.data.repository.FavoriteImageRepository
import com.jhkim.core.model.ImageWithFavoriteStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoriteImageRepository: FavoriteImageRepository
) {

    operator fun invoke(): Flow<List<ImageWithFavoriteStatus>> {
        return favoriteImageRepository.favoriteImages.map {
            it.map { imageResource ->
                ImageWithFavoriteStatus(
                    imageResource = imageResource,
                    isFavorite = true
                )
            }
        }
    }

}