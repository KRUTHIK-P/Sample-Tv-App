package com.example.myapplicationtv.di

import android.content.Context
import com.example.myapplicationtv.data.api.KtorClient
import com.example.myapplicationtv.data.database.VideoDao
import com.example.myapplicationtv.data.database.VideoDatabase
import com.example.myapplicationtv.data.preferences.UserPreferenceManager
import com.example.myapplicationtv.data.repository.VideoRepositoryImpl
import com.example.myapplicationtv.domain.repository.VideoRepository
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