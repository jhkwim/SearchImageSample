package com.jhkim.core.data.repository

import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getImageResources(
        query: String
    ): Flow<List<ImageResource>>

}