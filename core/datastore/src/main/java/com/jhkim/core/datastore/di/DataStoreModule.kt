package com.jhkim.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.jhkim.core.datastore.FavoriteImagePreferences
import com.jhkim.core.datastore.FavoriteImagePreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton fun provideImageResourceDataStore(
        @ApplicationContext context: Context,
    ): DataStore<FavoriteImagePreferences> =
        DataStoreFactory.create(
            serializer = FavoriteImagePreferencesSerializer,
        ) {
            context.dataStoreFile("favorite_image_preferences.pb")
        }

}