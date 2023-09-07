package com.jhkim.core.data.repository

import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchImages(query: String): Flow<List<ImageResource>>

    fun searchMore(): Flow<List<ImageResource>>

}