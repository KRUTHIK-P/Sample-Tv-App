package com.example.myapplicationtv.domain.repository

import com.example.myapplicationtv.data.database.VideoEntity
import com.example.myapplicationtv.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun fetchVideosFromServer(): Flow<NetworkResult<List<VideoEntity>>>

    fun getAllVideosFromDb(): Flow<List<VideoEntity>>
    suspend fun insertVideosToDb(videos: List<VideoEntity>)
    suspend fun updateTheme()
    fun getTheme(): Flow<Boolean>
}