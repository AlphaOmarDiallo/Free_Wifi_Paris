package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model

import com.alphaomardiallo.freewifiparis.common.presentation.ErrorInfoUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.HotSpotsUi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.ResultUi

data class WifiHotSpotsUiState(
    val wifiHotSpots: HotSpotsUi = HotSpotsUi(),
    val hotSpotsList: MutableList<ResultUi> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: ErrorInfoUi = ErrorInfoUi(),
)
