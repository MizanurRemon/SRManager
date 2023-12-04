package com.srmanager.app.GPS

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun GpsStatus(gpsStatus: MutableState<Boolean>) {
    // var gpsStatus by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column {
        //Text(text = gpsStatus, modifier = Modifier.padding(16.dp))
        GpsStatusChecker(context = context) { status ->
            gpsStatus.value = status
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun GpsStatusChecker(
    context: Context,
    onStatusChanged: (Boolean) -> Unit
) {
    DisposableEffect(context) {
        val locationManager = ContextCompat.getSystemService(
            context,
            LocationManager::class.java
        )

        val listener = object : android.location.LocationListener {
            override fun onLocationChanged(location: android.location.Location) {
                // Not used for checking GPS status, but you can implement
            }

            override fun onProviderDisabled(provider: String) {
                onStatusChanged(false)
            }

            override fun onProviderEnabled(provider: String) {
                onStatusChanged(true)
            }


        }


        val isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
        onStatusChanged(isGpsEnabled!!)


        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000L,
            0f,
            listener
        )

        // This effect will be called on disposal of the composable
        onDispose {
            locationManager.removeUpdates(listener)
        }
    }
}