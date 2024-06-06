package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.GeoPoint2dUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.GeoShapeUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.GeometryUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.HotSpotsUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.ResultUi

data class HotSpots(
    val totalCount: Int,
    val hotSpotsList: List<Result>,
) {
    fun toHotSpotsUi() = HotSpotsUi(
        totalCount = totalCount,
        hotSpotsList = hotSpotsList.map { it.toResultUi() }
    )
}

data class Result(
    val streetAddress: String,
    val postalCode: String,
    val status: String,
    val geoPoint: GeoPoint2d,
    val geoShape: GeoShape,
    val id: String?,
    val siteName: String,
    val numberOfWifiHotspots: Int,
) {
    fun toResultUi() = ResultUi(
        streetAddress = streetAddress,
        postalCode = postalCode,
        status = status,
        geoPoint = geoPoint.toGeoPoint2dUi(),
        geoShape = geoShape.toGeoShapeUi(),
        id = id,
        siteName = siteName,
        numberOfWifiHotspots = numberOfWifiHotspots,
    )
}

data class GeoPoint2d(
    val lat: Double,
    val lon: Double,
) {
    fun toGeoPoint2dUi() = GeoPoint2dUi(
        lat = lat,
        lon = lon,
    )
}

data class GeoShape(
    val geometry: Geometry,
    val type: String,
) {
    fun toGeoShapeUi() = GeoShapeUi(
        geometry = geometry.toGeometryUi(),
        type = type,
    )
}

data class Geometry(
    val coordinates: List<Double>,
    val type: String,
) {
    fun toGeometryUi() = GeometryUi(
        coordinates = coordinates,
        type = type,
    )
}
