package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.datasource

import com.alphaomardiallo.freewifiparis.common.data.DataResponse
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.model.HotSpotsDto
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.api.HotSpotsApi
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent

class HotSpotsDataSource(private val hotSpotsApi: HotSpotsApi) : KoinComponent {

    suspend fun getHotSpots(postCode: String): DataResponse<HotSpotsDto> {
        val response = hotSpotsApi.fetchHotSpots(postCode)

        return when (response.status) {
            HttpStatusCode.OK -> DataResponse.Success(response = response.body<HotSpotsDto>())
            else -> DataResponse.Error(
                errorCode = response.status.value,
                description = response.status.description
            )
        }
    }
}
