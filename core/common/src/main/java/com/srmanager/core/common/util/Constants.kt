package com.srmanager.core.common.util

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Browser
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

const val MINIMUM_DISTANCE_FOR_CHECKOUT = 300
const val INTERNAL_ERROR = -1
const val EMAIL_REGEX =
    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
const val NUMBER_REGEX = "-?\\d+(\\.\\d+)?"
const val DATE_FORMAT = "yyyy-MM-dd"

const val APP_SETTINGS = "app_settings"
const val LANGUAGE_KEY = "language_key"

val ETHNICITIES = listOf(
    "Malayu",
    "Chines",
    "Indian",
    "Bangla",
    "Others"
)

val PAYMENT_OPTIONS = listOf(
    "Cash",
    "Credit"
)

val ROUTE_NAMES = listOf(
    "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"
)

enum class RouteName(val value: String) {
    SUNDAY("SUN"), MONDAY("MON"), TUESDAY("TUE"), WEDNESDAY("WED"), THURSDAY("THU"), FRIDAY("FRI"), SATURDAY(
        "SAT"
    )
}

fun intent(browserPackage: String, redirectUrl: String): Intent {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(addPrefixToUrl(redirectUrl)))
        intent.setPackage(browserPackage)
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, browserPackage)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        )
        intent
    } catch (e: ActivityNotFoundException) {
        // the expected browser is not installed
        Intent(Intent.ACTION_VIEW, Uri.parse(addPrefixToUrl(redirectUrl)))
    }
}

fun addPrefixToUrl(url: String): String {
    var newUrl = url
    if (!newUrl.startsWith("https://")) {
        newUrl = "https://$newUrl"
    }
    return newUrl
}

fun openExternalLink(url: String, context: Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (_: Exception) {

    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat(DATE_FORMAT)
    return formatter.format(Date(millis))
}

fun currentDate(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT)
    return sdf.format(Date())
}

fun fileImageUriToBase64(imageUri: Uri?, resolver: ContentResolver): String {

    return try {
        val selectedImage = BitmapFactory.decodeStream(resolver.openInputStream(imageUri!!))
        val byteArrayOutputStream = ByteArrayOutputStream()
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)

        return Base64.encodeToString(
            byteArrayOutputStream.toByteArray(),
            Base64.DEFAULT
        ).replace("\n", "")
    } catch (e: Exception) {
        ""
    }
}


fun base64ToImage(imageString: String): Bitmap {
    val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
    val options = BitmapFactory.Options().apply {
        inScaled = false // Disable scaling
        inDensity = 0 // Set density to default (original density)
        inTargetDensity = 0 // Set target density to default (original density)
    }

    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, options)
}

fun bitMapToString(bitmap: Bitmap): String {
    return try {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT).replace("\n", "")
    } catch (e: Exception) {
        ""
    }
}

fun calculationDistance(
    latitude: String,
    longitude: String,
    myLatitude: String,
    myLongitude: String
): Double {
    try {
        val earthRadius = 6371 // Earth radius in kilometers

        val dLat = Math.toRadians(myLatitude.toDouble() - latitude.toDouble())
        val dLon = Math.toRadians(myLongitude.toDouble() - longitude.toDouble())

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(latitude.toDouble())) * cos(Math.toRadians(myLatitude.toDouble())) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance = (earthRadius * c) * 1000
        return String.format("%.2f", distance).toDouble()
    } catch (e: Exception) {
        return 0.0
    }
}

fun routeNameFinalize(existingRouteName: String, newRoute: String): String {
    return when {
        !existingRouteName.contains(RouteName.valueOf(newRoute).value) -> {
            when {
                existingRouteName.isNotEmpty() -> ", ${RouteName.valueOf(newRoute).value}"
                else -> RouteName.valueOf(newRoute).value
            }
        }

        else -> {
            ""
        }
    }
}