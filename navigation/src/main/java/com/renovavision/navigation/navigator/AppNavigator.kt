package com.renovavision.navigation.navigator

import com.renovavision.domain.models.Photo
import com.renovavision.domain.models.Video
import com.renovavision.feature.main.ui.screen.MainScreenFragmentDirections
import com.renovavision.navigation.extensions.navigateSafe
import com.renovavision.navigation.navigation.PhotoNavigation
import com.renovavision.navigation.navigation.VideoNavigation

class AppNavigator : IAppNavigator(), PhotoNavigation, VideoNavigation {

    override fun fromMainToPhotoDetails(photo: Photo) {
        navController?.navigateSafe(MainScreenFragmentDirections.fromMainScreenToPhotoDetails(photo))
    }

    override fun fromMainToVideoDetails(video: Video) {
        navController?.navigateSafe(MainScreenFragmentDirections.fromMainScreenToVideoDetails(video))
    }
}