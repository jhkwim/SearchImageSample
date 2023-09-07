package com.jhkim.core.data.repository

import com.jhkim.core.common.network.CommonDispatchers
import com.jhkim.core.common.network.Dispatcher
import com.jhkim.core.model.ImageResource
import com.jhkim.core.model.ResultImageResources
import com.jhkim.core.network.ImageNetworkDataSource
import com.jhkim.core.network.model.NetworkImageResource
import com.jhkim.core.network.model.NetworkVClipResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @Dispatcher(CommonDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: ImageNetworkDataSource
) : ImageRepository {

    override fun searchImages(query: String, page: Int) = flow {
        emit(datasource.getImageResource(query, page).mapResultImageResources())
    }.flowOn(ioDispatcher)

    override fun searchVClips(query: String, page: Int) = flow {
        emit(datasource.getVClipResource(query, page).mapResultImageResources())
    }.flowOn(ioDispatcher)

}

private fun NetworkImageResource.mapResultImageResources(): ResultImageResources {
    return ResultImageResources(
        imageResources = documents
            .map {
                ImageResource(url = it.thumbnailUrl, dateTime = it.datetime)
            },
        isEnd = meta.isEnd
    )
}

private fun NetworkVClipResource.mapResultImageResources(): ResultImageResources {
    return ResultImageResources(
        imageResources = documents
            .map {
                ImageResource(url = it.thumbnail, dateTime = it.datetime)
            },
        isEnd = meta.isEnd
    )
}