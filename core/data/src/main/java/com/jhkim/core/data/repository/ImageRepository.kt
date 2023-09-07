package com.jhkim.core.data.repository

import com.jhkim.core.model.ResultImageResources
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun searchImages(query: String, page: Int): Flow<ResultImageResources>

    fun searchVClips(query: String, page: Int): Flow<ResultImageResources>

}