package com.jhkim.core.domain

import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.model.ImageWithFavoriteStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SearchImageWithFavoriteStatusUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator fun invoke(query: String): Flow<List<ImageWithFavoriteStatus>> {
        return combine(
            imageRepository.getImageResources(query),
            imageRepository.favoriteImages
        ) { searchedImage, favoriteImages ->
            searchedImage
                .map { imageResource ->
                    ImageWithFavoriteStatus(
                        imageResource = imageResource,
                        isFavorite = favoriteImages.any { it.imageResource == imageResource }
                    )
                }
        }
    }

}