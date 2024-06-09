package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.HotSpotsMarkerUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.ResultUi
import org.koin.core.component.KoinComponent

class ToHotSpotsMarkerUiUseCase : KoinComponent {

    fun execute(hotSpot: ResultUi) = HotSpotsMarkerUi(
        streetAddress = hotSpot.streetAddress,
        postalCode = hotSpot.postalCode,
        status = hotSpot.status,
        geoPoint = hotSpot.geoPoint,
        geoShape = hotSpot.geoShape,
        id = hotSpot.id ?: "",
        siteName = hotSpot.siteName,
        numberOfWifiHotspots = hotSpot.numberOfWifiHotspots
    )
}