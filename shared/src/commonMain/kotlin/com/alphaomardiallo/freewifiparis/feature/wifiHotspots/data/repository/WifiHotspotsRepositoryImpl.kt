package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.repository

import com.alphaomardiallo.freewifiparis.common.data.DataResponse
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.model.HotSpotsDto
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.datasource.HotSpotsDataSource
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.repository.WifiHotspotsRepository

class WifiHotspotsRepositoryImpl(
    private val hotSpotsDataSource: HotSpotsDataSource,
) : WifiHotspotsRepository {

    override suspend fun getWifiHotspots(postcode: String): DataResponse<HotSpotsDto> =
        hotSpotsDataSource.getHotSpots(postcode)
}
