package com.srmanager.app.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationLiveData(private var context: Context) : LiveData<LocationDetails>() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val geoCoder = Geocoder(context, Locale.getDefault())
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onActive() {
        super.onActive()
        if (!hasLocationPermissions()) return
        
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location.also {
                setLocationData(it)
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

    private suspend fun getAddressWithRetry(
        location: Location,
        maxRetries: Int = 3,
        initialDelay: Long = 1000
    ): String = suspendCoroutine { continuation ->
        val retryCount = 0

        fun attemptGeocoding() {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // New callback-based API (Android 13+)
                    Geocoder(context, Locale.getDefault()).getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    ) { addresses ->
                        if (addresses.isNotEmpty()) {
                            continuation.resume(buildAddressString(addresses[0]))
                        } else {
                            handleGeocodingFailure(
                                continuation,
                                retryCount,
                                maxRetries,
                                initialDelay,
                                ::attemptGeocoding
                            )
                        }
                    }
                } else {
                    // Legacy API
                    @Suppress("DEPRECATION")
                    val addresses = geoCoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                    if (addresses?.isNotEmpty() == true) {
                        continuation.resume(buildAddressString(addresses[0]))
                    } else {
                        handleGeocodingFailure(
                            continuation,
                            retryCount,
                            maxRetries,
                            initialDelay,
                            ::attemptGeocoding
                        )
                    }
                }
            } catch (e: Exception) {
                handleGeocodingFailure(
                    continuation,
                    retryCount,
                    maxRetries,
                    initialDelay,
                    ::attemptGeocoding
                )
            }
        }

        attemptGeocoding()
    }

    private fun handleGeocodingFailure(
        continuation: Continuation<String>,
        retryCount: Int,
        maxRetries: Int,
        currentDelay: Long,
        retryFunction: () -> Unit
    ) {
        if (retryCount < maxRetries) {
            Handler(Looper.getMainLooper()).postDelayed(
                retryFunction,
                currentDelay * (retryCount + 1) // Exponential backoff
            )
        } else {
            continuation.resume("") // Return empty string after all retries fail
        }
    }

    private fun buildAddressString(address: Address): String {
        return StringBuilder().apply {
            for (i in 0..address.maxAddressLineIndex) {
                append(address.getAddressLine(i)).append(" ")
            }
        }.toString().trim()
    }

    private fun setLocationData(location: Location) {
        scope.launch { 
            try {
                val address = getAddressWithRetry(location)
                postValue(LocationDetails(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    address = address
                ))
            }catch (e: Exception){
                postValue(LocationDetails(
                    location.latitude,
                    location.longitude,
                    ""
                ))

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
            locationResult.locations.forEach { location ->
                setLocationData(location)
            }
        }
    }

    companion object {
        private const val ONE_MINUTE: Long = 60000

        val locationRequest: LocationRequest =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // New Builder pattern for Android 12 (API 31) and higher
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, ONE_MINUTE).apply {
                    setMinUpdateIntervalMillis(ONE_MINUTE / 4)
                    // Add other configuration as needed
                }.build()
            } else {
                // Legacy approach for older versions
                @Suppress("DEPRECATION")
                LocationRequest.create().apply {
                    interval = ONE_MINUTE
                    fastestInterval = ONE_MINUTE / 4
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            }
    }

}