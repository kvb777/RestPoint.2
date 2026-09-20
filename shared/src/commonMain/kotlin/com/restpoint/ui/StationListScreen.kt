package com.restpoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.restpoint.AppStrings
import com.restpoint.model.CrowdLevel
import com.restpoint.model.RestStation
import com.restpoint.viewmodel.StationListUiState

@Composable
fun StationListScreen(
    state: StationListUiState,
    strings: AppStrings,
    onStationClick: (RestStation) -> Unit,
    onRefresh: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        HeatStatusBanner(isRestricted = state.heatStatus.isRestrictedWindow, warningText = strings.heatWarning)

        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(strings.nearbyStationsTitle, style = MaterialTheme.typography.headlineSmall)
            TextButton(onClick = onRefresh) { Text(strings.refreshButton) }
        }

        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.errorMessage != null -> {
                Text(
                    state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                LazyColumn {
                    items(state.stations) { station ->
                        StationRow(station = station, strings = strings, onClick = { onStationClick(station) })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun HeatStatusBanner(isRestricted: Boolean, warningText: String) {
    if (!isRestricted) return
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF3CD))
            .padding(12.dp)
    ) {
        Text(
            "⚠️ $warningText",
            color = Color(0xFF664D03),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StationRow(station: RestStation, strings: AppStrings, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(station.name, style = MaterialTheme.typography.bodyLarge)
            Text(
                if (station.hasWorkingAc) "✅ ${strings.acWorking}" else "⚠️ ${strings.acDown}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        CrowdBadge(level = station.crowdLevel, strings = strings)
    }
}

@Composable
private fun CrowdBadge(level: CrowdLevel, strings: AppStrings) {
    val (label, color) = when (level) {
        CrowdLevel.LOW -> strings.crowdLow to Color(0xFF2E7D32)
        CrowdLevel.MEDIUM -> strings.crowdMedium to Color(0xFFF9A825)
        CrowdLevel.HIGH -> strings.crowdHigh to Color(0xFFC62828)
        CrowdLevel.UNKNOWN -> strings.crowdUnknown to Color.Gray
    }
    Box(
        Modifier
            .background(color.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(label, color = color, style = MaterialTheme.typography.labelSmall)
    }
}