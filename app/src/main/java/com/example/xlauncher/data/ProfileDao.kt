package com.example.xlauncher.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.xlauncher.models.MinecraftProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles ORDER BY lastPlayed DESC")
    fun getAllProfiles(): Flow<List<MinecraftProfile>>

    @Query("SELECT * FROM profiles WHERE id = :id LIMIT 1")
    suspend fun getProfileById(id: Int): MinecraftProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: MinecraftProfile): Long

    @Update
    suspend fun updateProfile(profile: MinecraftProfile)

    @Delete
    suspend fun deleteProfile(profile: MinecraftProfile)
}
