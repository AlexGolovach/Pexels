package com.renovavision.file_helper

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import kotlinx.coroutines.*
import java.io.File
import java.util.*

class FileHelper(private val context: Context) {

    fun downloadFile(uri: Uri, name: String? = null) {
        val downloadedFileName = name ?: uri.toString().substringAfterLast('/')

        val request = DownloadManager.Request(uri)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadedFileName)
            .setTitle(downloadedFileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        downloadManager?.enqueue(request)
    }

    fun convertBitmapToFile(coroutineScope: CoroutineScope, bitmap: Bitmap, onFailed: () -> Unit, onSuccess: (Uri) -> Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            runCatching {
                val file = File.createTempFile("IMG_${Date().time}_", ".jpg", context.cacheDir)
                file.writeBitmap(bitmap)

                delay(3000)

                val uri = FileProvider.getUriForFile(context, "com.renovavision.pexels.provider", file)

                withContext(Dispatchers.Main) {
                    onSuccess.invoke(uri)
                }
            }.getOrElse {
                withContext(Dispatchers.Main) {
                    onFailed.invoke()
                }
            }
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap) {
        outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    }
}