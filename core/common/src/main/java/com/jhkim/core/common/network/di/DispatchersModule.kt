package com.jhkim.core.common.network.di

import com.jhkim.core.common.network.CommonDispatchers
import com.jhkim.core.common.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(CommonDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Dispatcher(CommonDispatchers.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}