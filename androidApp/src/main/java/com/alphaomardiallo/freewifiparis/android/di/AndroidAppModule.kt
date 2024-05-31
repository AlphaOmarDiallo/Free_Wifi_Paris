package com.alphaomardiallo.freewifiparis.android.di

import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.WifiHotSpotsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val wifiHotSpotsModule = module {
    viewModelOf(::WifiHotSpotsViewModel)
}
