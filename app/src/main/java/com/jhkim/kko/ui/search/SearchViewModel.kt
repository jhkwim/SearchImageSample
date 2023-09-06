package com.jhkim.kko.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.ImageRepository
import com.jhkim.core.model.ImageResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    val text = MutableStateFlow("")

    private val _searchedImages = MutableStateFlow<List<ImageResource>>(listOf())
    val searchedImages: StateFlow<List<ImageResource>> = _searchedImages

    fun search() = viewModelScope.launch {
        imageRepository.getImageResources(text.value).collect {
            _searchedImages.value = it
        }
    }

}