package com.example.xlauncher.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xlauncher.ui.components.Sidebar
import com.example.xlauncher.ui.screens.HomeScreen
import com.example.xlauncher.ui.screens.VersionsScreen
import com.example.xlauncher.viewmodels.MainViewModel

@Composable
fun XLauncherApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.weight(1f)
            ) {
                composable("home") {
                    val profile by viewModel.activeProfile.collectAsState()
                    HomeScreen(profile = profile)
                }
                composable("versions") {
                    VersionsScreen(viewModel = viewModel)
                }
                composable("mods") {
                    Text("Mods Manager (Not Implemented)", color = MaterialTheme.colorScheme.onSurface)
                }
                composable("worlds") {
                    Text("Worlds Manager (Not Implemented)", color = MaterialTheme.colorScheme.onSurface)
                }
                composable("settings") {
                    Text("Settings (Not Implemented)", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}
