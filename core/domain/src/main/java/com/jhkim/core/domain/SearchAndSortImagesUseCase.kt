package com.jhkim.core.domain

import com.jhkim.core.common.network.Resource
import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import javax.inject.Inject

class SearchAndSortImagesUseCase @Inject constructor() {
    operator fun invoke(searchResult: Flow<List<ImageResource>>): Flow<Resource<List<ImageResource>>> =
        searchResult.mapToResource()
}

private fun Flow<List<ImageResource>>.mapToResource(): Flow<Resource<List<ImageResource>>> =
    map<List<ImageResource>, Resource<List<ImageResource>>> { searchResult ->
        Resource.Success(searchResult.sortByDateTime())
    }.onStart {
        emit(Resource.Loading())
    }.catch { throwable ->
        emit(Resource.Error(throwable.message ?: "search fail : unknown"))
    }

private fun List<ImageResource>.sortByDateTime() =
    sortedByDescending { imageResource ->
        val dateTimePattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        dateTimePattern.parse(imageResource.dateTime)
    }
