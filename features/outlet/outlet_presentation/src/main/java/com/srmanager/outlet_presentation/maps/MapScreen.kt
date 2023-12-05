package com.srmanager.outlet_presentation.maps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.srmanager.core.designsystem.R
import com.srmanager.core.designsystem.components.AppToolbarCompose


@Composable
fun MapScreen(onBack: () -> Unit) {
    val singapore = LatLng(23.822350, 90.365417)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 15f)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = R.drawable.ic_back,
            title = com.srmanager.core.common.R.string.back
        )
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "London",
                snippet = "Marker in Big Ben",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        }
    }
}


@Preview()
@Composable
fun MapScreenPreview() {
    MapScreen(onBack = {})
}