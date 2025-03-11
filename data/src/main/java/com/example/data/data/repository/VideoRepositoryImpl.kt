package com.example.data.data.repository

import android.util.Log
import com.example.core.model.VideoEntity
import com.example.core.repository.VideoRepository
import com.example.core.utils.NetworkResult
import com.example.data.data.database.VideoDao
import com.example.data.data.preferences.UserPreferenceManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VideoRepositoryImpl(
    private val client: HttpClient,
    private val videoDao: VideoDao,
    private val userPreferenceManager: UserPreferenceManager
) : VideoRepository {

    override suspend fun fetchVideosFromServer() = flow {
        emit(NetworkResult.Loading)
        try {
            val response = client.get("/api/v1/videos").body<List<VideoEntity>>()
            Log.d("apiCall", "GET: $response")
            emit(NetworkResult.Success(response))
        } catch (e: ClientRequestException) {
            // handle 4xx
            Log.d("apiCall", "GET error: $e")
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        } catch (e: ServerResponseException) {
            Log.d("apiCall", "GET error: $e")
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        } catch (e: Exception) {
            Log.d("apiCall", "GET error: $e")
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }

    }.catch {
        emit(NetworkResult.Error(it.message ?: "An error occurred"))
    }

    override fun getAllVideosFromDb(): Flow<List<VideoEntity>> {
        return videoDao.getAllVideos()
    }

    override suspend fun insertVideosToDb(videos: List<VideoEntity>) {
        videoDao.insertVideos(videos)
    }

    override suspend fun updateTheme() {
        userPreferenceManager.updateTheme()
    }

    override fun getTheme() = userPreferenceManager.darkTheme
}