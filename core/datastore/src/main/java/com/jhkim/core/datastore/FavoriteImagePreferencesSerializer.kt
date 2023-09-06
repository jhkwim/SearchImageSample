package com.jhkim.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object FavoriteImagePreferencesSerializer : Serializer<FavoriteImagePreferences> {

    override val defaultValue: FavoriteImagePreferences = FavoriteImagePreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FavoriteImagePreferences {
        try {
            return FavoriteImagePreferences.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FavoriteImagePreferences, output: OutputStream) = t.writeTo(output)
}