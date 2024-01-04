package com.srmanager.outlet_presentation.maps.Single

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.srmanager.core.designsystem.R
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.network.dto.Outlet


@Composable
fun MapScreen(onBack: () -> Unit, viewModel: MapsViewModel = hiltViewModel(), outletDetails: Outlet? = null) {
    val context = LocalContext.current
    var polylinePoints by remember { mutableStateOf(emptyList<LatLng>()) }

    val destination = LatLng(outletDetails!!.latitude.toDouble(), outletDetails.longitude.toDouble())
    val initialPosition = LatLng(viewModel.state.latitude, viewModel.state.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(destination, 15f)
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
                state = MarkerState(position = destination),
                title = outletDetails.outletName,
                snippet = outletDetails.ownerName,
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

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        100,
        100,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
