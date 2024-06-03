package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.domain.ToHotSpotsMarkerUiUseCase
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model.WifiHotSpotsUiState
import com.alphaomardiallo.freewifiparis.common.domain.DomainResponse
import com.alphaomardiallo.freewifiparis.common.presentation.ErrorInfoUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase
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
            getWifiHotspotsUseCase.invoke().collect { response ->

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
                        _uiState.update { state ->
                            state.copy(
                                wifiHotSpots = response.response.toHotSpotsUi(),
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

    fun getMarkers() = _uiState.value.wifiHotSpots.hotSpotsList.map {
        toHotSpotsMarkerUiUseCase.execute(it)
    }
}
