package com.renovavision.photo_details.viewModel

import android.content.Intent
import android.graphics.Bitmap
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.base.SingleLiveData
import com.renovavision.file_helper.FileHelper

class PhotoDetailsViewModel(private val fileHelper: FileHelper) : ViewModel() {

    private val shareImageLiveData = SingleLiveData<Intent>()
    val shareImage: LiveData<Intent>
        get() = shareImageLiveData

    private val showProgressLiveData = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = showProgressLiveData

    fun loadImage(imageUrl: String) {
        val uri = imageUrl.toUri()
        fileHelper.downloadFile(uri)
    }

    fun shareFile(bitmap: Bitmap) {
        showProgressLiveData.value = true
        fileHelper.convertBitmapToFile(viewModelScope, bitmap, {
            showProgressLiveData.value = false
        }, {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, it)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            showProgressLiveData.value = false
            shareImageLiveData.value = Intent.createChooser(intent, "Share image to...")
        })
    }

    fun shareLink(videoUrl: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, videoUrl)
        }

        shareImageLiveData.value = Intent.createChooser(intent, "Share link to...")
    }
}