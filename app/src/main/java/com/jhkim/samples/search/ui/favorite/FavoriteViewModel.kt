package com.jhkim.samples.search.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.FavoriteImageRepository
import com.jhkim.core.domain.GetFavoriteImagesUseCase
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
    private val favoriteImageRepository: FavoriteImageRepository,
    getFavoriteImagesUseCase: GetFavoriteImagesUseCase
) : ViewModel() {

    private val _favoriteImages = getFavoriteImagesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    val favoriteImages: StateFlow<List<ImageWithFavoriteStatus>> = _favoriteImages

    fun removeFavoriteImage(imageResource: ImageResource) = viewModelScope.launch {
        favoriteImageRepository.removeFavoriteImageResource(imageResource)
    }
}