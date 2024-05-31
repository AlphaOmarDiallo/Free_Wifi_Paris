package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots

import androidx.lifecycle.ViewModel
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase

class WifiHotSpotsViewModel(
    private val getWifiHotspotsUseCase: GetWifiHotspotsUseCase,
) : ViewModel() {

}