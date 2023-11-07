package com.srmanager.core.network.interceptor

import com.srmanager.core.common.util.DEFAULT_LANGUAGE_TAG
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.core.network.di.RestConfig
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response


class PublicInterceptor(private val preferenceDataStoreHelper: PreferenceDataStoreHelper) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        var tag = runBlocking {
            preferenceDataStoreHelper.getFirstPreference(
                PreferenceDataStoreConstants.LANGUAGE_TAG,
                DEFAULT_LANGUAGE_TAG
            )
        }

        val tagUrl = if (tag == DEFAULT_LANGUAGE_TAG) RestConfig.SERVER_URL_NL else RestConfig.SERVER_URL_EN

        val originalUrl: HttpUrl = originalRequest.url

        val newUrl: HttpUrl = tagUrl.toHttpUrlOrNull()!!
            .newBuilder()
            .addPathSegments(originalUrl.encodedPath)
            .build()

        val newRequest =
            originalRequest.newBuilder()
                .url(newUrl)
                .addHeader("App-Type", "APP-ANDROID")
                .build()
        return chain.proceed(newRequest)
    }
}