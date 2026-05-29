package com.srmanager.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object PreferenceDataStoreConstants {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val COMPANY_ID = intPreferencesKey("company_id")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}