package com.example.feature.presentation.screen

import android.net.Uri
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.feature.presentation.viewmodel.VideoViewModel

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(viewModel: VideoViewModel) {
    val context = LocalContext.current
    val videoArgument by viewModel.videoArgument.collectAsState()
    val isLoading = remember { mutableStateOf(true) }

    videoArgument?.let { video ->
        // Initialize ExoPlayer
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            isLoading.value = false
                        }
                    }
                })
            }
        }

        LaunchedEffect(Unit) {
            try {
                // Log the URL
                Log.d("PlayerScreen", "Video URL: $video")

                // Fetch and log the content of the URL
//                withContext(Dispatchers.IO) {
//                    val urlContent = URL(video).readText()
//                    Log.d("PlayerScreen1", "URL Content: $urlContent")
//                }

                with(exoPlayer) {
                    val dataSourceFactory = DefaultHttpDataSource.Factory()
                    // Use ProgressiveMediaSource for mp4 video
                    val mediaSource =
                        androidx.media3.exoplayer.source.ProgressiveMediaSource.Factory(
                            dataSourceFactory
                        ).createMediaSource(
                            androidx.media3.common.MediaItem.Builder()
                                .setUri(Uri.parse(video))
                                .build()
                        )
                    setMediaSource(mediaSource)
                    prepare()

                    playWhenReady = true
                    videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                    repeatMode = Player.REPEAT_MODE_ONE
                }
            } catch (e: Exception) {
                Log.e("PlayerScreen2", "Error initializing ExoPlayer", e)
            }
        }

        Box {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            } else {
                AndroidView(
                    modifier = Modifier.focusable(),
                    factory = {
                        PlayerView(context).apply {
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                            player = exoPlayer
                            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        }
                    }
                )
            }
            DisposableEffect(
                Unit
            ) {
                onDispose { exoPlayer.release() }
            }
        }
    }
}
