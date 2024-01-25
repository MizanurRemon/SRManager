package com.srmanager.core.common.util

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.provider.Browser
import android.util.Base64
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


const val INTERNAL_ERROR = -1
const val EMAIL_REGEX =
    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
const val URL_REGEX =
    "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.][a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
const val ENGLISH_TAG = "en"
const val DEFAULT_LANGUAGE_TAG = "nl"
const val FAV_ICON_PREFIX_URL = "https://www.google.com/s2/favicons?sz=256&domain="
const val DATE_FORMAT = "yyyy-MM-dd"

const val APP_SETTINGS = "app_settings"
const val LANGUAGE_KEY = "language_key"
const val TERMS_SERVICE_URL = "https://internetpolice.com/general-terms-conditions/"
const val COMMUNITY_URL = "https://internetpolitie.nl/forums/"
const val PROFILE_PANEL_TEST = "https://profile-panel-test.internetpolice.com/"
const val PROFILE_PANEL_STAGING = "https://profile-panel-staging.internetpolice.com/"
const val WARNING_URL = PROFILE_PANEL_TEST
const val REDIRECT_URL = WARNING_URL + "website-warning"

const val TWITTER_URL = "https://twitter.com/IPoliceNL"
const val LINKEDIN_URL = "https://www.linkedin.com/company/internet-politie-b-v/"
const val FACEBOOK_URL = "https://www.facebook.com/InternetPolitie"
const val INSTAGRAM_URL = "https://www.instagram.com/internet_politie"

val AGE_LIST = listOf("-18", "18-34", "35-60", "60+")

val ETCHNICITIES = listOf(
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
    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
)

val MARKET_NAMES = listOf("Jamuna", "Basundhara", "Gulisthan", "DOHS Shopping Mall")


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
    } catch (e: Exception) {

    }
}

fun openMailBox(mail: String, subject: String, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, "")
    intent.data = Uri.parse(mail)

    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    try {
        context.startActivity(intent)
    } catch (e: Exception) {

    }
}

fun shareStringWithOthers(text: String, context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Choose Option")
    context.startActivity(shareIntent)
}

fun convertHtmlToText(text: String): String {
    return HtmlCompat.fromHtml(text, 0).toString()
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat(DATE_FORMAT)
    return formatter.format(Date(millis))
}

fun currentDate(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT)
    return sdf.format(Date())
}

fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {

    val db = ContextCompat.getDrawable(context, drawable)

    val bit = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bit)
    db.setBounds(0, 0, canvas.width, canvas.height)
    db.draw(canvas)
    return bit
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
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
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

fun bitMapToString(bitmap: Bitmap): String {
    return try {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    } catch (e: Exception) {
        ""
    }
}


fun SVGToBase64(input: String): String {
    try {
        val bytes = input.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }catch (e: Exception){
        Log.d("dataxx", "SVGToBase64: $e")

        return ""
    }
}