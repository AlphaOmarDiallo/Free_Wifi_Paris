package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model

import kotlinx.serialization.Serializable

data class HotSpots(
    val totalCount: Int,
    val hotSpotsList: List<Result>,
)

data class Result(
    val streetAddress: String,
    val postalCode: String,
    val status: String,
    val geoPoint: GeoPoint2d,
    val geoShape: GeoShape,
    val id: String,
    val siteName: String,
    val numberOfWifiHotspots: Int,
)

@Serializable
data class GeoPoint2d(
    val lat: Double,
    val lon: Double,
)

@Serializable
data class GeoShape(
    val geometry: Geometry,
    val type: String,
)

@Serializable
data class Geometry(
    val coordinates: List<Double>,
    val type: String,
)
