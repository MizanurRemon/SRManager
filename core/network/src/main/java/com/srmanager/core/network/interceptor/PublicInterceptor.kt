package com.srmanager.core.network.interceptor

import com.srmanager.core.network.di.RestConfig
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response


class PublicInterceptor :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()


        val originalUrl: HttpUrl = originalRequest.url

        val newUrl: HttpUrl = RestConfig.LOCAL_URL.toHttpUrlOrNull()!!
            .newBuilder()
            .addPathSegments(originalUrl.encodedPath)
            .build()

        val newRequest =
            originalRequest.newBuilder()
                .url(newUrl)
                //.addHeader("App-Type", "APP-ANDROID")
                .build()
        return chain.proceed(newRequest)
    }
}