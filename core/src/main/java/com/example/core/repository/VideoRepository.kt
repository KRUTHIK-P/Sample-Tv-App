package com.example.core.repository

import com.example.core.model.VideoEntity
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun fetchVideosFromServer(): Flow<NetworkResult<List<VideoEntity>>>

    fun getAllVideosFromDb(): Flow<List<VideoEntity>>
    suspend fun insertVideosToDb(videos: List<VideoEntity>)
    suspend fun updateTheme()
    fun getTheme(): Flow<Boolean>
}