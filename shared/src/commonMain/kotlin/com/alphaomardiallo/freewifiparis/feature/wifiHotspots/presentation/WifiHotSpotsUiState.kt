package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation

import com.alphaomardiallo.freewifiparis.common.presentation.ErrorInfoUi

data class WifiHotSpotsUiState(
    val wifiHotSpots: HotSpotsUi = HotSpotsUi(),
    val hotSpotsList: MutableList<ResultUi> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: ErrorInfoUi = ErrorInfoUi(),
    val markers: List<HotSpotsMarkerUi> = emptyList(),
    val selectedPositionLat: Double? = null,
    val selectedPositionLon: Double? = null,
)
