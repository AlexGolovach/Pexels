package com.renovavision.photos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.renovavision.domain.models.Photo
import com.renovavision.domain.use_cases.PhotosUseCase
import com.renovavision.navigation.navigation.PhotoNavigation

class PhotoListViewModel(topMoviesUseCase: PhotosUseCase, private val navigation: PhotoNavigation) : ViewModel() {

    val photos = topMoviesUseCase.invoke().cachedIn(viewModelScope)

    fun navToPhotoDetails(photo: Photo) {
        navigation.fromMainToPhotoDetails(photo)
    }
}