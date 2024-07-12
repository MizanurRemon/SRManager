package com.srmanager.app

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.srmanager.app.GPS.GpsStatus
import com.srmanager.app.connectivity.NetworkCallbackImpl
import com.srmanager.app.connectivity.NetworkStatusScreen
import com.srmanager.app.location.LocationLiveData
import com.srmanager.app.navigations.MainApp
import com.srmanager.core.designsystem.deviceHeight
import com.srmanager.core.designsystem.deviceWidth
import com.srmanager.core.designsystem.theme.BaseTheme
import com.srmanager.core.designsystem.theme.GpsStatusDialog
import com.srmanager.database.dao.LocationDao
import com.srmanager.database.entity.LocationEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

@RequiresApi(Build.VERSION_CODES.R)
private fun foregroundPermissionApproved(context: Context): Boolean {
    val writePermissionFlag =
        PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            context, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    val readPermissionFlag =
        PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        )

    val managePermissionFlag =
        PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            context, Manifest.permission.MANAGE_EXTERNAL_STORAGE
        )

    return writePermissionFlag && readPermissionFlag && managePermissionFlag
}

@RequiresApi(Build.VERSION_CODES.R)
private fun requestForegroundPermission(context: Context) {
    val provideRationale = foregroundPermissionApproved(context = context)
    if (provideRationale) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ),
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        )
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        )
    }
}


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val gpsStatus = mutableStateOf(true)
    private val permissionGranted = mutableStateOf(true)


    private val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    private lateinit var locationLiveData: LocationLiveData

    @Inject
    lateinit var locationDao: LocationDao

    private val REQUEST_LOCATION = 12
    private var newIntent: Intent? = null

    private val networkCallback = NetworkCallbackImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //adjustFontScale(resources.configuration)
        deviceWidth = with(resources.displayMetrics) {
            (widthPixels / density).toInt()
        }
        deviceHeight = with(resources.displayMetrics) {
            (heightPixels / density).toInt()
        }

        permissionGranted.value = checkPermissionsGranted()

        setContent {

            BaseTheme {
                val context = LocalContext.current
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    requestForegroundPermission(context)
                }
                MainApp()
                NetworkStatusScreen()
                if (permissionGranted.value) {
                    GpsStatus(gpsStatus = gpsStatus)

                    if (!gpsStatus.value) {
                        GpsStatusDialog(openDialog = gpsStatus, onClick = {
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        })
                    }
                }
            }
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getLocation() {
        locationLiveData = LocationLiveData(application)
        locationLiveData.observeForever {

            GlobalScope.launch(Dispatchers.IO) {
                locationDao.insertLocation(
                    LocationEntity(
                        latitude = it.latitude.toString(),
                        longitude = it.longitude.toString(),
                        address = it.address.toString()
                    )
                )
            }
        }
    }

   /* override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            newIntent = intent
        }
    }*/

    override fun onResume() {
        super.onResume()
        newIntent?.let {

            setContent {
                MainApp()

            }
            newIntent = null

        }

        checkLocationPermission()
    }

    override fun onStart() {
        super.onStart()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onStop() {
        super.onStop()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }


    private fun checkLocationPermission() {
        if (!checkPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest,
                REQUEST_LOCATION
            )
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permissionGranted.value = true
                    getLocation()
                } else {
                    checkLocationPermission()
                }
                return
            }
        }
    }

    private fun checkPermissionsGranted(): Boolean {

        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

}
