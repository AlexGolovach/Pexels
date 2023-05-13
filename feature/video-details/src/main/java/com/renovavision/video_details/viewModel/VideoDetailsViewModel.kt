package com.renovavision.video_details.viewModel

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.renovavision.base.SingleLiveData
import com.renovavision.file_helper.FileHelper

class VideoDetailsViewModel(private val fileHelper: FileHelper) : ViewModel() {

    private val shareVideoLiveData = SingleLiveData<Intent>()
    val shareVideo: LiveData<Intent>
        get() = shareVideoLiveData

    fun loadVideo(videoUrl: String) {
        val uri = videoUrl.toUri()
        val name = videoUrl.substringAfterLast('/').substringBeforeLast('?')
        fileHelper.downloadFile(uri, name)
    }

    fun shareLink(videoUrl: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, videoUrl)
        }

        shareVideoLiveData.value = Intent.createChooser(intent, "Share link to...")
    }
}