package com.example.xlauncher.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class MinecraftProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val minecraftVersion: String,
    val modLoader: String,
    val javaRuntime: String,
    val renderer: String,
    val minRamMb: Int,
    val maxRamMb: Int,
    val lastPlayed: Long = 0,
    val gameDirectory: String = "default"
)
