package com.jhkim.samples.search.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.common.network.Resource
import com.jhkim.core.data.repository.FavoriteImageRepository
import com.jhkim.core.data.repository.SearchRepository
import com.jhkim.core.domain.MapFavoriteImageUseCase
import com.jhkim.core.domain.SearchAndSortImagesUseCase
import com.jhkim.core.model.ImageResource
import com.jhkim.core.model.ImageWithFavoriteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val favoriteImageRepository: FavoriteImageRepository,
    private val searchAndSortImagesUseCase: SearchAndSortImagesUseCase,
    private val mapFavoriteImageUseCase: MapFavoriteImageUseCase
) : ViewModel() {

    val text = MutableStateFlow("")

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchedImages = MutableStateFlow<List<ImageWithFavoriteStatus>>(listOf())
    val searchedImages: StateFlow<List<ImageWithFavoriteStatus>> = _searchedImages

    fun search() = viewModelScope.launch(Dispatchers.IO) {
        _isLastPage.value = false
        searchAndSortImagesUseCase(searchRepository.searchImages(query = text.value)).collect {
            result(it, false)
        }
    }

    fun searchMore() = viewModelScope.launch(Dispatchers.IO) {
        searchAndSortImagesUseCase(searchRepository.searchMore()).collect {
            result(it, true)
        }
    }

    private suspend fun result(resource: Resource<List<ImageResource>>, isLoadMore: Boolean) {
        when (resource) {
            is Resource.Error -> {
                _isLoading.value = false
                Log.e("Search", resource.message)
            }

            is Resource.Loading -> _isLoading.value = true
            is Resource.Success -> {
                val result = resource.data

                if (result.isEmpty()) {
                    _isLastPage.value = true
                    _isLoading.value = false
                } else {
                    mapFavoriteImageUseCase(resource.data).collect { newList ->
                        if (isLoadMore) {
                            _searchedImages.update { originList -> originList + newList }
                        } else {
                            _searchedImages.value = newList
                        }
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun addFavoriteImage(imageResource: ImageResource) = viewModelScope.launch {
        favoriteImageRepository.addFavoriteImageResource(imageResource)
    }

    fun removeFavoriteImage(imageResource: ImageResource) = viewModelScope.launch {
        favoriteImageRepository.removeFavoriteImageResource(imageResource)
    }

}