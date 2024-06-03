package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.domain.ToHotSpotsMarkerUiUseCase
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model.WifiHotSpotsUiState
import com.alphaomardiallo.freewifiparis.common.domain.DomainResponse
import com.alphaomardiallo.freewifiparis.common.presentation.ErrorInfoUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.HotSpotsUi
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

    fun getWifiHotspots() {
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
                                    )
                                )
                            }

                            Napier.d(response.toString())
                        }
                    }
                }
            }
        }
    }

    fun getMarkers() = _uiState.value.hotSpotsList.map {
        toHotSpotsMarkerUiUseCase.execute(it)
    }

    private fun districtList() = listOf(
        "75001",
        "75002",
        "75003",
        "75004",
        "75005",
        "75006",
        "75007",
        "75008",
        "75009",
        "75010",
        "75011",
        "75012",
        "75013",
        "75014",
        "75015",
        "75016",
        "75017",
        "75018",
        "75019",
        "75020",
        "92100",
        "94380"
    )
}
