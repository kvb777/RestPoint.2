
package com.restpoint.viewmodel

import com.restpoint.data.MockRestStationRepository
import com.restpoint.data.RestStationRepository
import com.restpoint.model.HeatStatus
import com.restpoint.model.HeatWindowCalculator
import com.restpoint.model.RestStation
import com.restpoint.model.distanceKmFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StationListUiState(
    val isLoading: Boolean = true,
    val stations: List<RestStation> = emptyList(),
    val heatStatus: HeatStatus = HeatStatus(isRestrictedWindow = false),
    val errorMessage: String? = null
)

/**
 * Plain shared ViewModel — no androidx.lifecycle dependency required,
 * so it works identically in commonMain on every platform. Screens
 * observe `uiState` and call `loadStations()` / `reportStation()`.
 *
 * A real app would create one instance of this (e.g. via a simple
 * DI container or just `remember { StationListViewModel() }` in
 * App.kt) and keep it alive for the composable's lifetime.
 */
class StationListViewModel(
    private val repository: RestStationRepository = MockRestStationRepository(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _uiState = MutableStateFlow(StationListUiState())
    val uiState: StateFlow<StationListUiState> = _uiState.asStateFlow()

    init {
        loadStations()
    }

    fun loadStations(riderLat: Double? = null, riderLon: Double? = null) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                var stations = repository.getStations()
                if (riderLat != null && riderLon != null) {
                    stations = stations.sortedBy { it.distanceKmFrom(riderLat, riderLon) }
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    stations = stations,
                    heatStatus = HeatWindowCalculator.currentStatus()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Couldn't load stations: ${e.message}"
                )
            }
        }
    }

    fun reportStation(id: String, hasWorkingAc: Boolean, crowdLevel: com.restpoint.model.CrowdLevel) {
        scope.launch {
            repository.reportStation(id, hasWorkingAc, crowdLevel)
            loadStations() // refresh after reporting
        }
    }
}
