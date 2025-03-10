package com.example.myapplicationtv.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import androidx.tv.material3.Typography
import androidx.tv.material3.darkColorScheme
import androidx.tv.material3.lightColorScheme
import com.example.myapplicationtv.R
import com.example.myapplicationtv.data.database.VideoEntity
import com.example.myapplicationtv.presentation.theme.DarkBackground
import com.example.myapplicationtv.presentation.theme.DarkOnBackground
import com.example.myapplicationtv.presentation.theme.DarkOnPrimary
import com.example.myapplicationtv.presentation.theme.DarkOnSurface
import com.example.myapplicationtv.presentation.theme.DarkPrimary
import com.example.myapplicationtv.presentation.theme.DarkSurface
import com.example.myapplicationtv.presentation.theme.DarkSurfaceFocused
import com.example.myapplicationtv.presentation.theme.LightBackground
import com.example.myapplicationtv.presentation.theme.LightOnBackground
import com.example.myapplicationtv.presentation.theme.LightOnPrimary
import com.example.myapplicationtv.presentation.theme.LightOnSurface
import com.example.myapplicationtv.presentation.theme.LightPrimary
import com.example.myapplicationtv.presentation.theme.LightSurface
import com.example.myapplicationtv.presentation.theme.LightSurfaceFocused
import com.example.myapplicationtv.presentation.viewmodel.VideoViewModel
import com.example.myapplicationtv.utils.NetworkResult

@Composable
fun HomeScreen(viewModel: VideoViewModel, onItemClick: () -> Unit) {
    val videos by viewModel.videos.collectAsState()
    val isDarkMode by viewModel.darkMode.collectAsState()
    Log.d("theme", "$isDarkMode")

    val colorScheme = if (isDarkMode) {
        darkColorScheme(
            primary = DarkPrimary,
            onPrimary = DarkOnPrimary,
            background = DarkBackground,
            onBackground = DarkOnBackground,
            surface = DarkSurface,
            onSurface = DarkOnSurface,
            secondary = DarkSurfaceFocused
        )
    } else {
        lightColorScheme(
            primary = LightPrimary,
            onPrimary = LightOnPrimary,
            background = LightBackground,
            onBackground = LightOnBackground,
            surface = LightSurface,
            onSurface = LightOnSurface,
            secondary = LightSurfaceFocused
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(
            bodyMedium = TextStyle(
                color = colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (videos) {
                NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    }
                }

                is NetworkResult.Error -> {
                    Log.d("HomeScreen", "Api Error: ${(videos as? NetworkResult.Error)?.message}")
                    viewModel.loadVideosFromDb()
                }

                is NetworkResult.Success -> {
                    val data =
                        (videos as? NetworkResult.Success<List<VideoEntity>>)?.data ?: emptyList()
                    Home(data, viewModel, onItemClick)
                }
            }
        }
    }
}

@Composable
fun Home(
    data: List<VideoEntity>,
    viewModel: VideoViewModel,
    onItemClick: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = stringResource(R.string.featured_videos),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow {
                items(data) { video ->
                    VideoCard(video, viewModel, onItemClick)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.popular_videos),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow {
                items(data) { video ->
                    VideoCard(video, viewModel, onItemClick)
                }
            }
        }
    }
}

@Composable
fun VideoCard(video: VideoEntity, viewModel: VideoViewModel, onItemClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val cardBackgroundColor = if (isFocused) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.surface
    }
    Log.d("HomeScreen", "isFocused: ${video.title}  $isFocused")
    Card(
        modifier = Modifier
            .padding(12.dp)
            .width(200.dp)
            .height(250.dp)
            .clickable {
                viewModel.updateVideoArgument(video.videoUrl)
                onItemClick()
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .focusable(interactionSource = interactionSource)
                .background(cardBackgroundColor)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_media_play),
                contentDescription = video.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = video.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.TV_1080p)
@Composable
fun HomeScreenPreview() {
    HomeScreen(hiltViewModel()) {}
}
