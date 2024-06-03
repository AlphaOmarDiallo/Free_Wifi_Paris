package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.repository

import com.alphaomardiallo.freewifiparis.common.data.DataResponse
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.model.HotSpotsDto

interface WifiHotspotsRepository {

    suspend fun getWifiHotspots(postcode: String): DataResponse<HotSpotsDto>
}
