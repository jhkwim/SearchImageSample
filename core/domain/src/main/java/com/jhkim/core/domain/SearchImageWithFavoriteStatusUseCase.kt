package com.jhkim.core.domain

import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.model.ImageResource
import com.jhkim.core.model.ImageWithFavoriteStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SearchImageWithFavoriteStatusUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(query: String): Flow<List<ImageWithFavoriteStatus>> =
        imageRepository.getImageResources(query)
            .mapToImageWithFavoriteStatusList(imageRepository.favoriteImages)
}

private fun Flow<List<ImageResource>>.mapToImageWithFavoriteStatusList(favoriteImages: Flow<List<ImageResource>>): Flow<List<ImageWithFavoriteStatus>> =
    combine(favoriteImages) { searchedImage, favoriteImages ->
        searchedImage
            .map { imageResource ->
                ImageWithFavoriteStatus(
                    imageResource = imageResource,
                    isFavorite = imageResource in favoriteImages
                )
            }
    }
