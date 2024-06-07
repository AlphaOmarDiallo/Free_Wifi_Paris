package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alphaomardiallo.freewifiparis.android.R
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.composable.HotSpotsMarker
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.composable.IconColor
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.model.HotSpotsMarkerUi
import com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.viewmodel.WifiHotSpotsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KFunction2

private const val LATITUDE_PARIS = 48.8566
private const val LONGITUDE_PARIS = 2.3522
private const val DEFAULT_ZOOM_LEVEL = 11f
private const val STREET_ZOOM_LEVEL = 15f
private const val CAMERA_ANIMATION_DURATION = 1000

@Composable
fun WifiHotSpotsScreen() {
    val viewModel: WifiHotSpotsViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WifiHotSpotsContent(
        markers = uiState.value.markers,
        onItemCardClick = viewModel::savePosition,
        currentPosition = uiState.value.selectedPosition,
        deletePosition = viewModel::deletePosition
    )
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun WifiHotSpotsContent(
    markers: List<HotSpotsMarkerUi> = emptyList(),
    onItemCardClick: KFunction2<Double, Double, Unit>,
    currentPosition: LatLng?,
    deletePosition: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { markers.size })
    val coroutineContext = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        val paris = LatLng(LATITUDE_PARIS, LONGITUDE_PARIS)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(paris, DEFAULT_ZOOM_LEVEL)
        }

        GoogleMap(
            modifier = Modifier.fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                mapToolbarEnabled = false,
                zoomControlsEnabled = false,
            )
        ) {
            Clustering(
                items = markers,
                clusterItemContent = { hotSpot ->
                    HotSpotsMarker(
                        colors = IconColor().getIconColor(hotSpot.status == "Opérationnel"),
                        isOperational = hotSpot.status == "Opérationnel",
                    )
                },
                onClusterItemClick = {
                    coroutineContext.launch {
                        pagerState.animateScrollToPage(getHotSpotIndex(markers, it))
                    }
                    Napier.e(it.siteName)
                    false
                }
            )
        }

        HotSpotsPager(
            onclick = onItemCardClick,
            pagerState = pagerState,
            hotSpots = markers,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        currentPosition?.let {
            val zoom =
                if (cameraPositionState.position.zoom > STREET_ZOOM_LEVEL) cameraPositionState.position.zoom else STREET_ZOOM_LEVEL

            coroutineContext.launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newCameraPosition(
                        CameraPosition(it, zoom, 0f, 0f)
                    ), durationMs = CAMERA_ANIMATION_DURATION
                )
            }

            deletePosition.invoke()
        }
    }
}

@Composable
private fun HotSpotsPager(
    onclick: KFunction2<Double, Double, Unit>,
    pagerState: PagerState,
    hotSpots: List<HotSpotsMarkerUi>,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(state = pagerState, modifier = modifier.fillMaxWidth()) {
        val hotSpot = hotSpots[it]
        HotSpotItemCard(hotSpot = hotSpot, onclick = onclick)
    }
}

@Composable
private fun HotSpotItemCard(hotSpot: HotSpotsMarkerUi, onclick: KFunction2<Double, Double, Unit>) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (hotSpot.status == "Opérationnel") Color.Green else Color.Red
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = hotSpot.siteName,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2
                )
                Text(
                    text = String.format(
                        stringResource(id = R.string.status_format),
                        hotSpot.status
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = hotSpot.streetAddress, style = MaterialTheme.typography.bodySmall)
                Text(text = hotSpot.postalCode, style = MaterialTheme.typography.bodySmall)
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { onclick.invoke(hotSpot.geoPoint.lat, hotSpot.geoPoint.lon) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_searching_24),
                        contentDescription = "Show on the map"
                    )
                }

                Button(onClick = {
                    openMaps(
                        siteName = hotSpot.siteName,
                        latitude = hotSpot.geoPoint.lat,
                        longitude = hotSpot.geoPoint.lon,
                        context
                    )
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_navigation_24),
                        contentDescription = "Navigate to the place"
                    )
                }
            }
        }
    }
}

private fun getHotSpotIndex(markers: List<HotSpotsMarkerUi>, hotSpot: HotSpotsMarkerUi): Int {
    return markers.indexOf(hotSpot)
}

private fun openMaps(siteName: String, latitude: Double, longitude: Double, context: Context) {
    val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}?q=${siteName}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(context, mapIntent, null)
}
