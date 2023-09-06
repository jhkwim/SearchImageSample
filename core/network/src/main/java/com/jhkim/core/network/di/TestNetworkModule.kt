package com.jhkim.core.network.di

import com.jhkim.core.network.ImageNetworkDataSource
import com.jhkim.core.network.retrofit.RetrofitImageNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TestNetworkModule {

//    @Binds
//    fun RetrofitImageNetwork.binds(): ImageNetworkDataSource

}