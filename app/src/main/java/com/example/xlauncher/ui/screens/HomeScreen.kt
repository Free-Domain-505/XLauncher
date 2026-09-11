package com.example.xlauncher.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.xlauncher.managers.LaunchState
import com.example.xlauncher.managers.NativeLaunchManagerImpl
import com.example.xlauncher.models.MinecraftProfile
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(profile: MinecraftProfile?) {
    val coroutineScope = rememberCoroutineScope()
    var launchState by remember { mutableStateOf<LaunchState>(LaunchState.Idle) }
    
    val launchManager = remember { NativeLaunchManagerImpl() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        if (profile == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            return
        }

        Text(
            text = "Profile: ${profile.name}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard("Version", profile.minecraftVersion, Modifier.weight(1f))
            InfoCard("Loader", profile.modLoader, Modifier.weight(1f))
            InfoCard("Java", profile.javaRuntime, Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard("Renderer", profile.renderer, Modifier.weight(1f))
            InfoCard("RAM", "${profile.minRamMb} - ${profile.maxRamMb} MB", Modifier.weight(1f))
            InfoCard("Directory", profile.gameDirectory, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        when (val state = launchState) {
            is LaunchState.Idle -> {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            launchManager.launchProfile(profile).collect { newState ->
                                launchState = newState
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("PLAY MINECRAFT", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
            }
            is LaunchState.Verifying -> {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(state.message, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
            is LaunchState.Error -> {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("XLauncher could not start Minecraft.", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cause: ${state.cause}", color = MaterialTheme.colorScheme.onErrorContainer)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Suggested Fix: ${state.fix}", color = MaterialTheme.colorScheme.onErrorContainer)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Button(onClick = { launchState = LaunchState.Idle }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
            else -> {
                Text("Launching...", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
