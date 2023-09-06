package com.jhkim.core.domain

import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.model.ImageWithFavoriteStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteImages @Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator fun invoke(): Flow<List<ImageWithFavoriteStatus>> {
        return imageRepository.favoriteImages.map {
            it.map { imageResource ->
                ImageWithFavoriteStatus(
                    imageResource = imageResource,
                    isFavorite = true
                )
            }
        }
    }

}