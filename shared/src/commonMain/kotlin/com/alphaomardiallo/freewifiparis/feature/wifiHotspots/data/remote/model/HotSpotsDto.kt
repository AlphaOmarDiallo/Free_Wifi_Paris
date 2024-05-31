package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotSpotsDto(
    val results: List<Result>,
    @SerialName("total_count")
    val totalCount: Int,
)

@Serializable
data class Result(
    @SerialName("arc_adresse")
    val streetAddress: String,
    @SerialName("cp")
    val postalCode: String,
    @SerialName("etat2")
    val status: String,
    @SerialName("geo_point_2d")
    val geoPoint: GeoPoint2d,
    @SerialName("geo_shape")
    val geoShape: GeoShape,
    @SerialName("idpw")
    val id: String,
    @SerialName("nom_site")
    val siteName: String,
    @SerialName("nombre_de_borne_wifi")
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
