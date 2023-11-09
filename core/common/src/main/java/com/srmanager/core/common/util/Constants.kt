package com.srmanager.core.common.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Browser
import androidx.core.text.HtmlCompat
import com.srmanager.core.common.R

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
const val LINKEDIN_URL= "https://www.linkedin.com/company/internet-politie-b-v/"
const val FACEBOOK_URL= "https://www.facebook.com/InternetPolitie"
const val INSTAGRAM_URL = "https://www.instagram.com/internet_politie"

val AGE_LIST = listOf("-18", "18-34", "35-60", "60+")


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




