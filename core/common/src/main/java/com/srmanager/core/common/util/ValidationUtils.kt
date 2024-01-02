package com.srmanager.auth_presentation

import java.util.regex.Pattern

private const val EMAIL_REGEX ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
   // "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
private const val PHONE_NUMBER_REGEX = "^(?:\\+?60|0)?(?:(3\\d{2}|4\\d{1}|6\\d{1}|7\\d{1}|8\\d{1}|9\\d{1})-?\\d{7,8}|1[0-9]{8})\$"
private const val PASSWORD_REGEX =
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
private const val ZIP_CODE_REGEX = "^[0-9]{5}(?:-[0-9]{4})?$"
private const val URL_REGEX =
    "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.][a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"

val emailValidator: (String) -> Boolean = {
    isEmailValid(it)
}
val passwordValidator: (String) -> Boolean = {
    isPasswordValid(it)
}

fun isEmailValid(email: String): Boolean {
    val pattern = Pattern.compile(EMAIL_REGEX)
    val matcher = pattern.matcher(email)
    return matcher.matches()
}


fun isPhoneNumberValid(phoneNumber: String): Boolean {
    val pattern = Pattern.compile(PHONE_NUMBER_REGEX)
    val matcher = pattern.matcher(phoneNumber)
    return matcher.matches()
}

fun isPasswordValid(password: String): Boolean {
    val pattern = Pattern.compile(PASSWORD_REGEX)
    val matcher = pattern.matcher(password)
    return matcher.matches()
}

fun isZipCodeValid(zipCode: String): Boolean {
    val pattern = Pattern.compile(ZIP_CODE_REGEX)
    val matcher = pattern.matcher(zipCode)
    return matcher.matches()
}

fun isUrlValid(url: String): Boolean {
    val pattern = Pattern.compile(URL_REGEX)
    val matcher = pattern.matcher(url)
    return matcher.matches()
}

