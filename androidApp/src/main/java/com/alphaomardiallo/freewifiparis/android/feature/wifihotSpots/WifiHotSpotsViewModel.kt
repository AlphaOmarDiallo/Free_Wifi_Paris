package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphaomardiallo.freewifiparis.common.domain.DomainResponse
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class WifiHotSpotsViewModel(
    private val getWifiHotspotsUseCase: GetWifiHotspotsUseCase,
) : ViewModel() {

    fun getWifiHotspots() {
        viewModelScope.launch {
            getWifiHotspotsUseCase.invoke().collect {
                when (it) {
                    is DomainResponse.Error -> Napier.e(it.description.toString())
                    is DomainResponse.Loading -> Napier.i("Loading")
                    is DomainResponse.Success -> Napier.d(it.response.toString())
                }
            }
        }
    }
}
