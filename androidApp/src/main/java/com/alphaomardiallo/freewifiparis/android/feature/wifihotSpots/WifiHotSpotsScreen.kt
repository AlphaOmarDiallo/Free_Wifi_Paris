package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun WifiHotSpotsScreen() {
    val viewModel: WifiHotSpotsViewModel = koinViewModel()

    Button(onClick = { viewModel.getWifiHotspots() }) {
        Text(text = "HOTSPOTS")
    }
}