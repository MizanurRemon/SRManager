package com.srmanager.app

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
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
    val writePermissionFlag = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val readPermissionFlag = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
        context, Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val managePermissionFlag = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
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
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE),
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        )
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
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

    private var appUpdateManager: AppUpdateManager? = null
    private var installStateUpdatedListener: InstallStateUpdatedListener? = null

    private val networkCallback = NetworkCallbackImpl()

    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                unregisterInstallStateUpdListener()
            }
        }

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
                requestForegroundPermission(context)
                MainApp()
                NetworkStatusScreen()
                if (permissionGranted.value) {
                    GpsStatus(gpsStatus = gpsStatus)

                    if (!gpsStatus.value) {
                        GpsStatusDialog(openDialog = gpsStatus, onClick = {
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        })
                    }
                }
            }
        }

        //checkLocationPermission()
        //checkForAppUpdate()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        newIntent = intent
    }

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

    private fun checkForAppUpdate() {

        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager!!.appUpdateInfo

        installStateUpdatedListener =
            InstallStateUpdatedListener { installState ->
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                    appUpdateManager!!.completeUpdate()
                } else if (installState.installStatus() == InstallStatus.DOWNLOADING) {

                }
            }

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    appUpdateManager!!.registerListener(installStateUpdatedListener!!)
                    startAppUpdateFlexible(appUpdateInfo)
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    startAppUpdateFlexible(appUpdateInfo)
                }
            }
        }
    }

    private fun startAppUpdateImmediate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager!!.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
            )
        } catch (e: SendIntentException) {
            e.printStackTrace()
        }
    }

    private fun startAppUpdateFlexible(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager!!.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
            )
        } catch (e: SendIntentException) {
            e.printStackTrace()
            unregisterInstallStateUpdListener()
        }
    }

    private fun unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null) appUpdateManager!!.unregisterListener(
            installStateUpdatedListener!!
        )
    }

    private fun adjustFontScale(configuration: Configuration) {
        configuration.let {
            it.fontScale = .9.toFloat()
            val metrics: DisplayMetrics = resources.displayMetrics
            val wm: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density

            baseContext.applicationContext.createConfigurationContext(it)
            baseContext.resources.displayMetrics.setTo(metrics)

        }
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
