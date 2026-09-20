package com.restpoint

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.restpoint.model.RestStation
import com.restpoint.ui.StationDetailScreen
import com.restpoint.ui.StationListScreen
import com.restpoint.viewmodel.StationListViewModel

private val LightColors = lightColorScheme(
    primary = Color(0xFF5B4FE3),
    secondary = Color(0xFF03A9F4),
    background = Color(0xFFF7F5FF),
    surface = Color(0xFFFFFFFF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9D97FF),
    secondary = Color(0xFF4FC3F7),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

@Composable
fun App() {
    val viewModel = remember { StationListViewModel() }
    val uiState by viewModel.uiState.collectAsState()
    var selectedStation by remember { mutableStateOf<RestStation?>(null) }
    var isArabic by remember { mutableStateOf(false) }
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    val strings = if (isArabic) ArabicStrings else EnglishStrings
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    MaterialTheme(colorScheme = colors) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Surface(modifier = Modifier, color = MaterialTheme.colorScheme.background) {
                Box(Modifier.fillMaxSize().padding(top = 90.dp)) {
                    val current = selectedStation
                    if (current == null) {
                        StationListScreen(
                            state = uiState,
                            strings = strings,
                            onStationClick = { selectedStation = it },
                            onRefresh = { viewModel.loadStations() }
                        )
                    } else {
                        StationDetailScreen(
                            station = current,
                            strings = strings,
                            onBack = { selectedStation = null },
                            onSubmitReport = { acWorking, crowdLevel ->
                                viewModel.reportStation(current.id, acWorking, crowdLevel)
                            }
                        )
                    }

                    // Language toggle - top-right corner, always visible
                    Box(Modifier.align(Alignment.TopEnd).padding(8.dp)) {
                        TextButton(onClick = { isArabic = !isArabic }) {
                            Text(if (isArabic) "English" else "العربية")
                        }
                    }
                }
            }
        }
    }
}