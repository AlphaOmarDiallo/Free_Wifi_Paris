package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase

import com.alphaomardiallo.freewifiparis.common.data.DataResponse
import com.alphaomardiallo.freewifiparis.common.domain.DomainResponse
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.model.HotSpots
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.repository.WifiHotspotsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWifiHotspotsUseCase(
    private val wifiHotspotsRepository: WifiHotspotsRepository,
) {
    operator fun invoke(postCode: String = "75001"): Flow<DomainResponse<HotSpots>> = flow {
        emit(DomainResponse.Loading())

        when (val result = wifiHotspotsRepository.getWifiHotspots(postCode)) {
            is DataResponse.Success -> {
                emit(DomainResponse.Success(response = result.response.toHotSpots()))
            }

            is DataResponse.Error -> {
                emit(
                    DomainResponse.Error(
                        errorCode = result.errorCode,
                        description = result.description
                    )
                )
            }
        }
    }
}
