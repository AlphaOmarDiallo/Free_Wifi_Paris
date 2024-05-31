package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.repository.WifiHotspotsRepository

class GetWifiHotspotsUseCase constructor(
    private val wifiHotspotsRepository: WifiHotspotsRepository,
) {
    operator fun invoke() = wifiHotspotsRepository.getWifiHotspots()
}
