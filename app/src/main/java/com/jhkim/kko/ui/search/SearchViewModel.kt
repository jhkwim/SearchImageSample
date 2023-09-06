package com.jhkim.kko.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.domain.SearchImageWithFavoriteStatusUseCase
import com.jhkim.core.model.ImageWithFavoriteStatus
import com.jhkim.core.model.ImageResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val searchImageWithFavoriteStatusUseCase: SearchImageWithFavoriteStatusUseCase
) : ViewModel() {

    val text = MutableStateFlow("")

    private val _searchedImages = MutableStateFlow<List<ImageWithFavoriteStatus>>(listOf())
    val searchedImages: StateFlow<List<ImageWithFavoriteStatus>> = _searchedImages

    fun search() = viewModelScope.launch {
        searchImageWithFavoriteStatusUseCase(text.value).collect {
            _searchedImages.value = it
        }
    }

    fun addFavoriteImage(imageResource: ImageResource) = viewModelScope.launch {
        imageRepository.addFavoriteImageResource(imageResource)
    }

    fun removeFavoriteImage(imageResource: ImageResource) = viewModelScope.launch {
        imageRepository.removeFavoriteImageResource(imageResource)
    }

}