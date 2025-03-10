# Sample Tv App

MyApplicationTV is an Android TV application built using Jetpack Compose and ExoPlayer. It features
a light and dark theme, video playback, and a focusable UI for TV navigation.

## Features

- Light and Dark Theme
- Video Playback using ExoPlayer
- Focusable UI for TV navigation
- LazyColumn and LazyRow for displaying video lists

## Getting Started

### Prerequisites

- Android Studio
- Kotlin
- Gradle

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/KRUTHIK-P/Sample-Tv-App.git

2. Open the project in Android Studio.
3. Build the project to download dependencies.

### Running the Application

1. Connect your Android TV device or use an Android TV emulator.
2. Click on the "Run" button in Android Studio.

## Project Structure

app/src/main/java/com/example/myapplicationtv/presentation/theme/Color.kt: Defines the color schemes
for light and dark themes.
app/src/main/java/com/example/myapplicationtv/presentation/screen/HomeScreen.kt: Contains the
HomeScreen composable which displays the list of videos.
app/src/main/java/com/example/myapplicationtv/presentation/screen/PlayerScreen.kt: Contains the
PlayerScreen composable which handles video playback using ExoPlayer.
app/src/main/java/com/example/myapplicationtv/presentation/viewmodel/VideoViewModel.kt: ViewModel
for managing video data and UI state.

## Usage

### HomeScreen

The HomeScreen composable displays a list of videos in a LazyColumn and LazyRow. It uses the
VideoCard composable to display individual video items.

### PlayerScreen

The PlayerScreen composable initializes and manages ExoPlayer for video playback. It displays a
PlayerView for the video content.