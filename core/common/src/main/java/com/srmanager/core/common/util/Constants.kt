package com.srmanager.core.common.util

import android.R
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.provider.Browser
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import com.srmanager.core.common.R as CommonR


const val INTERNAL_ERROR = -1
const val EMAIL_REGEX =
    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
const val URL_REGEX =
    "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.][a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
const val ENGLISH_TAG = "en"
const val DEFAULT_LANGUAGE_TAG = "nl"
const val FAV_ICON_PREFIX_URL = "https://www.google.com/s2/favicons?sz=256&domain="
const val DATE_FORMAT = "dd/MM/yyyy"

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

val REPORT_SUBJECT_LIST = listOf(
    CommonR.string.report,
    CommonR.string.address,
    CommonR.string.app_name
)

enum class LanguageTagEnum(val tag: String) {
    English("en"),
    Dutch("nl"),
    German("de"),
    Spanish("es"),
    French("fr"),
    Italiano("it"),
}

enum class LanguageListEnum(val lang: String) {
    English("English"),
    Nederlands("Dutch"),
    Deutsch("German"),
    Español("Spanish"),
    Français("French"),
    Italiano("Italiano"),
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
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imgString = android.util.Base64.encodeToString(
            byteArrayOutputStream.toByteArray(),
            android.util.Base64.DEFAULT
        )
        Log.d("dataxx", "fileImageUriToBase64: $imgString")
        return imgString
    } catch (e: java.lang.Exception) {
        Log.d("dataxx", "ERORXX: " + e.message)
        ""
    }
}


fun base64ToImage(imageString: String): Bitmap {
    var imageBytes = android.util.Base64.decode(imageString, android.util.Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}