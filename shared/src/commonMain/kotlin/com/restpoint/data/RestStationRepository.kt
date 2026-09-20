package com.restpoint.data

import com.restpoint.model.CrowdLevel
import com.restpoint.model.RestStation

interface RestStationRepository {
    suspend fun getStations(): List<RestStation>
    suspend fun reportStation(id: String, hasWorkingAc: Boolean, crowdLevel: CrowdLevel)
}

/**
 * Mock implementation with real Dubai locations pulled from public
 * MoHRE/RTA coverage — enough to build and demo the UI against before
 * a real backend (Firebase/Supabase) is wired up in step 3.
 */
class MockRestStationRepository : RestStationRepository {

    private val stations = mutableListOf(
        RestStation(
            id = "1",
            name = "Sheikh Zayed Road – Jebel Ali Village Rest Stop",
            latitude = 25.0186,
            longitude = 55.1178,
            hasWorkingAc = true,
            crowdLevel = CrowdLevel.LOW,
            lastReportedAt = 0L,
            partner = "RTA"
        ),
        RestStation(
            id = "2",
            name = "Port Saeed – Al Muraqabat St Rest Stop",
            latitude = 25.2582,
            longitude = 55.3273,
            hasWorkingAc = true,
            crowdLevel = CrowdLevel.MEDIUM,
            lastReportedAt = 0L,
            partner = "RTA"
        ),
        RestStation(
            id = "3",
            name = "Ras Al Khor Industrial Area 2 Rest Stop",
            latitude = 25.1857,
            longitude = 55.3438,
            hasWorkingAc = false,
            crowdLevel = CrowdLevel.UNKNOWN,
            lastReportedAt = 0L,
            partner = "RTA"
        )
    )

    override suspend fun getStations(): List<RestStation> = stations.toList()

    override suspend fun reportStation(id: String, hasWorkingAc: Boolean, crowdLevel: CrowdLevel) {
        val index = stations.indexOfFirst { it.id == id }
        if (index != -1) {
            stations[index] = stations[index].copy(
                hasWorkingAc = hasWorkingAc,
                crowdLevel = crowdLevel
            )
        }
    }
}


