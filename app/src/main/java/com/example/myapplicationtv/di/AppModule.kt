package com.example.myapplicationtv.di

import android.content.Context
import com.example.core.repository.VideoRepository
import com.example.data.data.api.KtorClient
import com.example.data.data.database.VideoDao
import com.example.data.data.database.VideoDatabase
import com.example.data.data.preferences.UserPreferenceManager
import com.example.data.data.repository.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient() = KtorClient.client

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context) =
        VideoDatabase.getDatabase(context).videoDao()

    @Provides
    @Singleton
    fun provideRepository(
        client: HttpClient,
        videoDao: VideoDao,
        userPreferenceManager: UserPreferenceManager
    ): VideoRepository = VideoRepositoryImpl(client, videoDao, userPreferenceManager)
}