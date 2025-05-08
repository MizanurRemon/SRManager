package com.srmanager.core.common.util

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsHelper {
    fun logException(exception: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }
}