package com.example.domain.domain.usecase

import com.example.core.model.VideoEntity
import com.example.core.repository.VideoRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoUseCase @Inject constructor(private val videoRepository: VideoRepository) {
    suspend fun fetchVideos(): Flow<NetworkResult<List<VideoEntity>>> {
        return videoRepository.fetchVideosFromServer()  // Fetch from server and save to DB
    }

    fun getVideosFromDb(): Flow<List<VideoEntity>> {
        return videoRepository.getAllVideosFromDb()
    }

    suspend fun insertVideosToDb(videos: List<VideoEntity>) {
        videoRepository.insertVideosToDb(videos)
    }

    suspend fun updateTheme() {
        videoRepository.updateTheme()
    }

    fun getTheme() = videoRepository.getTheme()
}