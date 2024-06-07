package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.GeoPoint2dUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.GeoShapeUi
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class HotSpotsMarkerUi(
    val streetAddress: String = "",
    val postalCode: String = "",
    val status: String = "",
    val geoPoint: GeoPoint2dUi = GeoPoint2dUi(),
    val geoShape: GeoShapeUi = GeoShapeUi(),
    val id: String = "",
    val siteName: String = "",
    val numberOfWifiHotspots: Int = 0,
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(geoPoint.lat, geoPoint.lon)

    override fun getTitle(): String? = null

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float = 1f
}
