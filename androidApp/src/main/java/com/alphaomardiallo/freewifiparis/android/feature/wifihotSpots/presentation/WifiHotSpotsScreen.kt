package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.composable.HotSpotsMarker
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.composable.IconColor
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model.HotSpotsMarkerUi
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.viewmodel.WifiHotSpotsViewModel
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.ResultUi
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.koinViewModel

private const val LATITUDE_PARIS = 48.8566
private const val LONGITUDE_PARIS = 2.3522
private const val DEFAULT_ZOOM_LEVEL = 11f

@Composable
fun WifiHotSpotsScreen() {
    val viewModel: WifiHotSpotsViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WifiHotSpotsContent(
        totalCount = uiState.value.wifiHotSpots.totalCount,
        hotSpots = uiState.value.wifiHotSpots.hotSpotsList,
        markers = viewModel.getMarkers()
    )
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun WifiHotSpotsContent(
    totalCount: Int = 0,
    hotSpots: List<ResultUi> = emptyList(),
    markers: List<HotSpotsMarkerUi> = emptyList(),
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val paris = LatLng(LATITUDE_PARIS, LONGITUDE_PARIS)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(paris, DEFAULT_ZOOM_LEVEL)
        }

        GoogleMap(
            modifier = Modifier.fillMaxWidth(),
            cameraPositionState = cameraPositionState
        ) {

            Clustering(
                items = markers,
                clusterItemContent = { hotSpot ->
                    HotSpotsMarker(
                        colors = IconColor().getIconColor(hotSpot.status == "Opérationnel"),
                        isOperational = hotSpot.status == "Opérationnel"
                    )
                }
            )
        }
    }
}
