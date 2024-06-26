package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.domain.ToHotSpotsMarkerUiUseCase
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model.WifiHotSpotsUiState
import com.alphaomardiallo.freewifiparis.common.domain.DomainResponse
import com.alphaomardiallo.freewifiparis.common.presentation.ErrorInfoUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.utils.ParisDistrictList.districtList
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.HotSpotsUi
import com.google.android.gms.maps.model.LatLng
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WifiHotSpotsViewModel(
    private val getWifiHotspotsUseCase: GetWifiHotspotsUseCase,
    private val toHotSpotsMarkerUiUseCase: ToHotSpotsMarkerUiUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WifiHotSpotsUiState())
    val uiState: StateFlow<WifiHotSpotsUiState> = _uiState

    init {
        getWifiHotspots()
    }

    fun savePosition(latitude: Double, longitude: Double) {
        _uiState.update { state ->
            state.copy(
                selectedPosition = LatLng(latitude, longitude)
            )
        }
    }

    fun deletePosition() {
        _uiState.update { state ->
            state.copy(
                selectedPosition = null
            )
        }
    }

    private fun getWifiHotspots() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                    hotSpotsList = mutableListOf(),
                    wifiHotSpots = HotSpotsUi(),
                    error = ErrorInfoUi(
                        code = null,
                        message = null
                    )
                )
            }

            districtList().map { district ->
                getWifiHotspotsUseCase.invoke(district).collect { response ->

                    when (response) {
                        is DomainResponse.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = ErrorInfoUi(
                                        code = response.errorCode,
                                        message = response.description
                                    )
                                )
                            }

                            Napier.e(response.description.toString())
                        }

                        is DomainResponse.Loading -> {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = true,
                                    error = ErrorInfoUi(
                                        code = null,
                                        message = null
                                    )
                                )
                            }

                            Napier.i("Loading")
                        }

                        is DomainResponse.Success -> {
                            val list = uiState.value.hotSpotsList
                            response.response.toHotSpotsUi().hotSpotsList.map { list.add(it) }

                            _uiState.update { state ->
                                state.copy(
                                    wifiHotSpots = response.response.toHotSpotsUi(),
                                    hotSpotsList = list,
                                    isLoading = false,
                                    error = ErrorInfoUi(
                                        code = null,
                                        message = null
                                    ),
                                    markers = getMarkers()
                                )
                            }

                            Napier.d(response.toString())
                        }
                    }
                }
            }
        }
    }

    private fun getMarkers() = _uiState.value.hotSpotsList.map {
        toHotSpotsMarkerUiUseCase.execute(it)
    }
}
