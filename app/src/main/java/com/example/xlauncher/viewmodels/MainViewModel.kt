package com.example.xlauncher.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.xlauncher.api.MinecraftVersion
import com.example.xlauncher.api.RetrofitInstance
import com.example.xlauncher.data.AppDatabase
import com.example.xlauncher.models.MinecraftProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val profileDao = AppDatabase.getDatabase(application).profileDao()

    val allProfiles: StateFlow<List<MinecraftProfile>> = profileDao.getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _activeProfile = MutableStateFlow<MinecraftProfile?>(null)
    val activeProfile: StateFlow<MinecraftProfile?> = _activeProfile.asStateFlow()

    private val _availableVersions = MutableStateFlow<List<MinecraftVersion>>(emptyList())
    val availableVersions: StateFlow<List<MinecraftVersion>> = _availableVersions.asStateFlow()

    private val _isLoadingVersions = MutableStateFlow(false)
    val isLoadingVersions: StateFlow<Boolean> = _isLoadingVersions.asStateFlow()

    init {
        viewModelScope.launch {
            allProfiles.collect { profiles ->
                if (profiles.isNotEmpty() && _activeProfile.value == null) {
                    _activeProfile.value = profiles.first()
                } else if (profiles.isEmpty()) {
                    createDefaultProfile()
                }
            }
        }
    }

    private suspend fun createDefaultProfile() {
        val defaultProfile = MinecraftProfile(
            name = "Latest Release",
            minecraftVersion = "1.20.4",
            modLoader = "Vanilla",
            javaRuntime = "Java 17",
            renderer = "OpenGL (GL4ES)",
            minRamMb = 1024,
            maxRamMb = 4096
        )
        profileDao.insertProfile(defaultProfile)
    }

    fun fetchVersions() {
        if (_availableVersions.value.isNotEmpty()) return
        
        viewModelScope.launch {
            _isLoadingVersions.value = true
            try {
                val manifest = RetrofitInstance.mojangApi.getVersionManifest()
                _availableVersions.value = manifest.versions
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoadingVersions.value = false
            }
        }
    }

    fun selectProfile(profile: MinecraftProfile) {
        _activeProfile.value = profile
    }
}
