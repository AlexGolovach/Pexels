package com.renovavision.data.mappers

import com.renovavision.data.models.UserResponse
import com.renovavision.data.models.VideoFileResponse
import com.renovavision.data.models.VideoResponse
import com.renovavision.data.models.VideosResponse
import com.renovavision.domain.models.User
import com.renovavision.domain.models.Video
import com.renovavision.domain.models.VideoFile
import com.renovavision.domain.models.Videos

val videosMapper: FunctionMapper<VideoResponse, Video> = {
    Video(
        id = it.id,
        image = it.image,
        user = it.user?.let { user -> userMapper(user) },
        videoFile = it.videoFiles?.let { files -> videoFileMapper(files) }
    )
}

val userMapper: FunctionMapper<UserResponse, User> = {
    User(
        id = it.id,
        name = it.name,
        url = it.url
    )
}

val videoFileMapper: FunctionMapper<List<VideoFileResponse>, VideoFile> = { list ->
    val file = list.first()

    VideoFile(
        id = file.id,
        fileType = file.fileType,
        link = file.link
    )
}