package com.renovavision.videos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.renovavision.domain.models.Video
import com.renovavision.domain.use_cases.VideosUseCase
import com.renovavision.navigation.navigation.VideoNavigation

class VideoListViewModel(useCase: VideosUseCase, private val navigation: VideoNavigation) : ViewModel() {

    val videos = useCase.invoke().cachedIn(viewModelScope)

    fun navToVideoDetails(video: Video) {
        navigation.fromMainToVideoDetails(video)
    }
}