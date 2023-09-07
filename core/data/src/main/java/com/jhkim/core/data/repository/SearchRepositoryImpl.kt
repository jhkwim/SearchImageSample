package com.jhkim.core.data.repository

import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val MIN_PAGE = 1
private const val MAX_PAGE = 50

class SearchRepositoryImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : SearchRepository {

    private var currentQuery = ""
    private var page = MIN_PAGE

    private var isEndImage = false
    private var isEndVClip = false

    override fun searchImages(query: String): Flow<List<ImageResource>> {
        currentQuery = query
        page = MIN_PAGE

        return combineSearch()
    }

    override fun searchMore(): Flow<List<ImageResource>> = if (page == MAX_PAGE) {
        flowOf(listOf())
    } else {
        combineSearch()
    }

    private fun combineSearch() = combine(
        imageRepository.searchImages(currentQuery, page + 1),
        imageRepository.searchVClips(currentQuery, page + 1)
    ) { resultImages, resultVClips ->

        val images = if (isEndImage) listOf() else resultImages.imageResources
        val vClips = if (isEndVClip) listOf() else resultVClips.imageResources

        isEndImage = resultImages.isEnd
        isEndVClip = resultVClips.isEnd

        if (!isEndImage || !isEndVClip) {
            page++
        }

        images + vClips
    }
}