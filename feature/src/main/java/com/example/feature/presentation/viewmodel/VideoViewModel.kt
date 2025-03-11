package com.example.feature.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.VideoEntity
import com.example.core.utils.NetworkManager
import com.example.core.utils.NetworkResult
import com.example.domain.domain.usecase.VideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videosUseCase: VideoUseCase,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _videos = MutableStateFlow<NetworkResult<List<VideoEntity>>>(NetworkResult.Loading)
    val videos: StateFlow<NetworkResult<List<VideoEntity>>> get() = _videos

    private val _videoArgument = MutableStateFlow<String?>(null)
    val videoArgument: StateFlow<String?> get() = _videoArgument

    private val _darkMode = MutableStateFlow(false)
    val darkMode: StateFlow<Boolean> get() = _darkMode

    init {
        if (NetworkManager.isInternetConnected(context)) {
            Log.d("internet", "connected")
            fetchVideos()
        } else {
            Log.d("internet1", "not connected")
            loadVideosFromDb()
        }

        viewModelScope.launch {
            videosUseCase.getTheme().collect { darkMode ->
                _darkMode.value = darkMode // Update the StateFlow
            }
        }
    }

    fun loadVideosFromDb() {
        viewModelScope.launch {
            videosUseCase.getVideosFromDb().collect { videoList ->
                _videos.value = NetworkResult.Success(videoList)
                Log.d("internet2", "from db")
            }
        }
    }

    private fun fetchVideos() {
        viewModelScope.launch {
            videosUseCase.fetchVideos() // Fetch new data from server
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    _videos.value = result
                    Log.d("internet3", "from api")

                    if (result is NetworkResult.Success) {
                        insertVideosToDb(result.data)
                    }
                }
        }
    }

    private fun insertVideosToDb(videos: List<VideoEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            videosUseCase.insertVideosToDb(videos)
        }
    }

    fun updateVideoArgument(video: String) {
        _videoArgument.value = video
    }

    fun updateTheme() {
        viewModelScope.launch {
            videosUseCase.updateTheme()
        }
    }
}
