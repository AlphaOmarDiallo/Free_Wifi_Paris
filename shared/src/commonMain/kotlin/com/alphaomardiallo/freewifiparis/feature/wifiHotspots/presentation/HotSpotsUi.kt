package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation

data class HotSpotsUi(
    val totalCount: Int = 0,
    val hotSpotsList: List<ResultUi> = emptyList(),
)

data class ResultUi(
    val streetAddress: String = "",
    val postalCode: String = "",
    val status: String = "",
    val geoPoint: GeoPoint2dUi = GeoPoint2dUi(),
    val geoShape: GeoShapeUi = GeoShapeUi(),
    val id: String = "",
    val siteName: String = "",
    val numberOfWifiHotspots: Int = 0,
)

data class GeoPoint2dUi(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)

data class GeoShapeUi(
    val geometry: GeometryUi = GeometryUi(),
    val type: String = "",
)

data class GeometryUi(
    val coordinates: List<Double> = emptyList(),
    val type: String = "",
)
