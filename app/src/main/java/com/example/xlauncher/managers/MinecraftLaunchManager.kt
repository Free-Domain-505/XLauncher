package com.example.xlauncher.managers

import com.example.xlauncher.models.MinecraftProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class LaunchState {
    object Idle : LaunchState()
    data class Verifying(val message: String) : LaunchState()
    data class Downloading(val current: Int, val total: Int, val fileName: String) : LaunchState()
    data class ExtractingNatives(val progress: Float) : LaunchState()
    data class Launching(val status: String) : LaunchState()
    data class Running(val processId: Int) : LaunchState()
    data class Error(val cause: String, val fix: String) : LaunchState()
}

interface MinecraftLaunchManager {
    fun launchProfile(profile: MinecraftProfile): Flow<LaunchState>
    fun abortLaunch()
}

class NativeLaunchManagerImpl : MinecraftLaunchManager {
    override fun launchProfile(profile: MinecraftProfile): Flow<LaunchState> = flow {
        emit(LaunchState.Verifying("Verifying profile ${profile.name}..."))
        kotlinx.coroutines.delay(500)
        
        emit(LaunchState.Verifying("Checking Minecraft version ${profile.minecraftVersion}..."))
        kotlinx.coroutines.delay(500)

        // Simulate checking natives and failure due to missing GL4ES/Vulkan on this setup
        emit(LaunchState.Error(
            cause = "Native execution engine (libxlauncher_core.so) is missing or unsupported on this architecture.",
            fix = "Please ensure the Android NDK components are compiled and GL4ES/Vulkan translation layers are installed in the data directory."
        ))
    }

    override fun abortLaunch() {
        // Stop current launch process
    }
}
