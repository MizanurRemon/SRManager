package com.srmanager.outlet_presentation.maps.Multiple

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.srmanager.core.designsystem.R
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AllOutletMapScreen(onBack: () -> Unit, viewModel: AllOutletMapViewModel = hiltViewModel()) {

    if (viewModel.state.isLocationFetched) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    viewModel.state.myLatitude,
                    viewModel.state.myLongitude
                ), 10f
            )
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
                repeat(viewModel.state.outletList.size) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                viewModel.state.outletList[it].latitude.toDouble(),
                                viewModel.state.outletList[it].longitude.toDouble()
                            )
                        ),
                        title = viewModel.state.outletList[it].outletName,
                        snippet = viewModel.state.outletList[it].ownerName,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                }

            }

        }
    }

    if (viewModel.state.isLoading) {
        LoadingDialog {}
    }
}