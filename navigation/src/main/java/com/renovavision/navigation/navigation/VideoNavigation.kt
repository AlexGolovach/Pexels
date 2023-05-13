package com.renovavision.navigation.navigation

import com.renovavision.domain.models.Video

interface VideoNavigation {

    fun fromMainToVideoDetails(video: Video)
}