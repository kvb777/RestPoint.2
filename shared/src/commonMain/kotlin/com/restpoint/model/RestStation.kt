package com.restpoint.model

import kotlinx.serialization.Serializable

@Serializable
data class RestStation(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hasWorkingAc: Boolean,
    val crowdLevel: CrowdLevel,
    val lastReportedAt: Long,
    val partner: String // e.g. "RTA Metro Station", "Careem Partner Site"
)

enum class CrowdLevel { LOW, MEDIUM, HIGH, UNKNOWN }

/**
 * Simple haversine distance in kilometers between two coordinates.
 * Pure Kotlin, no platform dependencies — used to sort stations by
 * proximity once we have the rider's current location.
 */
fun RestStation.distanceKmFrom(lat: Double, lon: Double): Double {
    val earthRadiusKm = 6371.0
    val dLat = Math.toRadians(latitude - lat)
    val dLon = Math.toRadians(longitude - lon)
    val a = kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) +
            kotlin.math.cos(Math.toRadians(lat)) * kotlin.math.cos(Math.toRadians(latitude)) *
            kotlin.math.sin(dLon / 2) * kotlin.math.sin(dLon / 2)
    val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
    return earthRadiusKm * c
}
