package com.jhkim.kko.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.domain.GetFavoriteImages
import com.jhkim.core.model.ImageResource
import com.jhkim.core.model.ImageWithFavoriteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    getFavoriteImages: GetFavoriteImages
) : ViewModel() {

    private val _favoriteImages = getFavoriteImages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    val favoriteImages: StateFlow<List<ImageWithFavoriteStatus>> = _favoriteImages

    fun removeFavoriteImage(imageResource: ImageResource) = viewModelScope.launch {
        imageRepository.removeFavoriteImageResource(imageResource)
    }
}