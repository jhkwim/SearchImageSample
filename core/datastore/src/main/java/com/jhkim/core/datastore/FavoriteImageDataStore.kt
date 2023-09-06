package com.jhkim.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.jhkim.core.model.ImageResource
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class FavoriteImageDataStore @Inject constructor(
    private val favoriteImagePreferences: DataStore<FavoriteImagePreferences>
) {
    val imageResources = favoriteImagePreferences.data
        .map {
            it.favoriteImagePreferencesList.map { favoriteImagePreference ->
                ImageResource(
                    url = favoriteImagePreference.url,
                    dateTime = favoriteImagePreference.dateTime
                )
            }
        }

    suspend fun addFavoriteImage(imageResource: ImageResource) {
        try {
            favoriteImagePreferences.updateData {
                it.copy {
                    favoriteImagePreferences.add(
                        FavoriteImagePreference.newBuilder()
                            .setUrl(imageResource.url)
                            .setDateTime(imageResource.dateTime)
                            .build()
                    )
                }
            }
        } catch (ioException: IOException) {
            Log.e(
                "FavoriteImagePreferences",
                "Failed to add favorite image preferences",
                ioException
            )
        }
    }

    suspend fun removeFavoriteImage(imageResource: ImageResource) {
        try {
            favoriteImagePreferences.updateData { currentPreferences ->
                val removedList =
                    currentPreferences.favoriteImagePreferencesList.filter { favoriteImagePreference ->
                        favoriteImagePreference.url != imageResource.url
                    }

                currentPreferences.toBuilder().clearFavoriteImagePreferences()
                    .addAllFavoriteImagePreferences(removedList).build()
            }
        } catch (ioException: IOException) {
            Log.e(
                "FavoriteImagePreferences",
                "Failed to remove favorite image preferences",
                ioException
            )
        }
    }
}