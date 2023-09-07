package com.jhkim.core.data.di

import com.jhkim.core.data.repository.FavoriteImageRepository
import com.jhkim.core.data.repository.FavoriteImageRepositoryImpl
import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.data.repository.ImageRepositoryImpl
import com.jhkim.core.data.repository.SearchRepository
import com.jhkim.core.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsImageRepository(
        imageRepository: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    fun bindsFavoriteImageRepository(
        favoriteImageRepository: FavoriteImageRepositoryImpl
    ): FavoriteImageRepository

    @Binds
    fun bindsSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository

}