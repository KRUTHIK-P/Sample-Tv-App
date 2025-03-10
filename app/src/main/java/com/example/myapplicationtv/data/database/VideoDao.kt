package com.example.myapplicationtv.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Query("SELECT * FROM video_table")
    fun getAllVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM video_table WHERE id = :id")
    fun getVideoById(id: Long): Flow<VideoEntity>

    @Delete
    suspend fun delete(video: VideoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)
}
