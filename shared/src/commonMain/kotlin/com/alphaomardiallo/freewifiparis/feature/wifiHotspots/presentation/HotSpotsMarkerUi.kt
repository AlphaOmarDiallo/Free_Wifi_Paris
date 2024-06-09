package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation

data class HotSpotsMarkerUi(
    val streetAddress: String = "",
    val postalCode: String = "",
    val status: String = "",
    val geoPoint: GeoPoint2dUi = GeoPoint2dUi(),
    val geoShape: GeoShapeUi = GeoShapeUi(),
    val id: String = "",
    val siteName: String = "",
    val numberOfWifiHotspots: Int = 0,
)
