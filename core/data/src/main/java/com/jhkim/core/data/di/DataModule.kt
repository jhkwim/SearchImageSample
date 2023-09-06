package com.jhkim.core.data.di

import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.data.repository.ImageRepositoryImpl
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
    ) : ImageRepository

}