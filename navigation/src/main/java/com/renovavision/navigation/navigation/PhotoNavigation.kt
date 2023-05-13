package com.renovavision.navigation.navigation

import com.renovavision.domain.models.Photo

interface PhotoNavigation {

    fun fromMainToPhotoDetails(photo: Photo)
}