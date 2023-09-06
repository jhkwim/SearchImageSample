package com.jhkim.core.data.repository

import com.jhkim.core.common.network.CommonDispatchers
import com.jhkim.core.common.network.Dispatcher
import com.jhkim.core.model.ImageResource
import com.jhkim.core.network.ImageNetworkDataSource
import com.jhkim.core.network.model.NetworkImageResource
import com.jhkim.core.network.model.NetworkVClipResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @Dispatcher(CommonDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: ImageNetworkDataSource
) : ImageRepository {

    override fun getImageResources(query: String): Flow<List<ImageResource>> = flow {

        val images = datasource.getImageResource(query).mapImageResources()
        val vClips = datasource.getVClipResource(query).mapImageResources()

        val result = images + vClips
        val sortedResult = result.sortedByDescending { imageResource ->
            val dateTimePattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            dateTimePattern.parse(imageResource.dateTime)
        }

        emit(sortedResult)

    }.flowOn(ioDispatcher)
}

private fun NetworkImageResource.mapImageResources(): List<ImageResource> {
    return documents.map {
        ImageResource(url = it.thumbnailUrl, dateTime = it.datetime)
    }
}

private fun NetworkVClipResource.mapImageResources(): List<ImageResource> {
    return documents.map {
        ImageResource(url = it.thumbnail, dateTime = it.datetime)
    }
}