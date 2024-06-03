package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.model

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model.GeoPoint2d
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model.GeoShape
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model.Geometry
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model.HotSpots
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model.Result
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotSpotsDto(
    @SerialName("results")
    val resultDto: List<ResultDto>,
    @SerialName("total_count")
    val totalCount: Int,
) {
    fun toHotSpots() = HotSpots(
        totalCount = totalCount,
        hotSpotsList = resultDto.map { it.toResult() }
    )
}

@Serializable
data class ResultDto(
    @SerialName("arc_adresse")
    val streetAddress: String,
    @SerialName("cp")
    val postalCode: String,
    @SerialName("etat2")
    val status: String,
    @SerialName("geo_point_2d")
    val geoPoint: GeoPoint2dDto,
    @SerialName("geo_shape")
    val geoShapeDto: GeoShapeDto,
    @SerialName("idpw")
    val id: String,
    @SerialName("nom_site")
    val siteName: String,
    @SerialName("nombre_de_borne_wifi")
    val numberOfWifiHotspots: Int,
) {
    fun toResult() = Result(
        streetAddress = streetAddress,
        postalCode = postalCode,
        status = status,
        geoPoint = geoPoint.toGeoPoint2d(),
        geoShape = geoShapeDto.toGeoShape(),
        id = id,
        siteName = siteName,
        numberOfWifiHotspots = numberOfWifiHotspots,
    )
}

@Serializable
data class GeoPoint2dDto(
    val lat: Double,
    val lon: Double,
) {
    fun toGeoPoint2d() = GeoPoint2d(
        lat = lat,
        lon = lon,
    )
}

@Serializable
data class GeoShapeDto(
    val geometry: GeometryDto,
    val type: String,
) {
    fun toGeoShape() = GeoShape(
        geometry = geometry.toGeometry(),
        type = type,
    )
}

@Serializable
data class GeometryDto(
    val coordinates: List<Double>,
    val type: String,
) {
    fun toGeometry() = Geometry(
        coordinates = coordinates,
        type = type,
    )
}
