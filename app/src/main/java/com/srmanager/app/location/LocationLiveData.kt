package com.srmanager.app.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

@Suppress("DEPRECATION")
class LocationLiveData(private var context: Context) : LiveData<LocationDetails>() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val scope = (context as? LifecycleOwner)?.lifecycleScope
        ?: CoroutineScope(Dispatchers.Main + SupervisorJob())

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onActive() {
        super.onActive()
        if (!hasLocationPermissions()) return

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                setLocationData(location = it, context = context)
            }
        }

        startLocationUpdates()
    }


    private fun hasLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startLocationUpdates() {
        if (!hasLocationPermissions()) return

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.getMainLooper()
        )
    }

    private fun setLocationData(location: Location?, context: Context) {
        location.let { data ->

            scope.launch {
                if (data != null) {
                    value = LocationDetails(
                        data.latitude,
                        data.longitude,
                        getAddressFromLocation(location = data, context = context)
                    )
                }
            }

        }

    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            // Get the lifecycleScope from your Activity/Fragment
            val lifecycleScope = (context as? LifecycleOwner)?.lifecycleScope
                ?: CoroutineScope(Dispatchers.Main) // Fallback if context isn't LifecycleOwner

            lifecycleScope.launch {
                locationResult.locations.forEach { location ->
                    setLocationData(location = location, context = context)
                }
            }
        }
    }

    companion object {
        private const val ONE_MINUTE: Long = 60000
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = ONE_MINUTE
            fastestInterval = ONE_MINUTE / 4
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private suspend fun getAddressFromLocation(context: Context, location: Location): String {
        return withContext(Dispatchers.IO) {
            try {
                if (!Geocoder.isPresent()) {
                    return@withContext "Geocoder not available"
                }

                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                if (addresses.isNullOrEmpty()) {
                    return@withContext "No address found"
                }

                val address = addresses[0]
                val addressParts =
                    (0..address.maxAddressLineIndex).map { address.getAddressLine(it) }
                addressParts.joinToString(", ").trim()
            } catch (e: IOException) {
                "Unknown"
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }
}