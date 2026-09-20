package com.restpoint.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.restpoint.AppStrings
import com.restpoint.model.CrowdLevel
import com.restpoint.model.RestStation

@Composable
fun StationDetailScreen(
    station: RestStation,
    strings: AppStrings,
    onBack: () -> Unit,
    onSubmitReport: (hasWorkingAc: Boolean, crowdLevel: CrowdLevel) -> Unit
) {
    var acWorking by remember { mutableStateOf(station.hasWorkingAc) }
    var crowdLevel by remember { mutableStateOf(station.crowdLevel) }
    var submitted by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text(strings.backButton) }

        Text(station.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(4.dp))
        Text(station.partner, style = MaterialTheme.typography.bodySmall)

        Spacer(Modifier.height(24.dp))
        Text(strings.reportSectionTitle, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(strings.isAcWorkingQuestion, Modifier.weight(1f))
            Switch(checked = acWorking, onCheckedChange = { acWorking = it })
        }

        Spacer(Modifier.height(16.dp))
        Text(strings.crowdLevelQuestion)
        Spacer(Modifier.height(8.dp))
        Column {
            CrowdLevel.entries.filter { it != CrowdLevel.UNKNOWN }.forEach { level ->
                val label = when (level) {
                    CrowdLevel.LOW -> strings.crowdLow
                    CrowdLevel.MEDIUM -> strings.crowdMedium
                    CrowdLevel.HIGH -> strings.crowdHigh
                    else -> level.name
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = crowdLevel == level,
                        onClick = { crowdLevel = level }
                    )
                    Text(label)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                onSubmitReport(acWorking, crowdLevel)
                submitted = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(strings.submitReportButton)
        }

        if (submitted) {
            Spacer(Modifier.height(12.dp))
            Text(
                "✅ ${strings.reportSubmittedThanks}",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}