package com.srmanager.core.network.di

import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.di.qualifier.PublicNetwork
import com.srmanager.core.network.interceptor.PublicInterceptor
import com.srmanager.core.network.util.ResultCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PublicNetworkModule {

    @Provides
    @Singleton
    @PublicNetwork(TypeEnum.INTERCEPTOR)
    fun provideInterceptor(preferenceDataStoreHelper: PreferenceDataStoreHelper): PublicInterceptor {
        return PublicInterceptor(preferenceDataStoreHelper)
    }

    @Provides
    @Singleton
    @PublicNetwork(TypeEnum.OKHTTP)
    fun provideOkHttpClient(@PublicNetwork(TypeEnum.INTERCEPTOR) interceptor: PublicInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            this.addInterceptor(httpLoggingInterceptor).addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    @PublicNetwork(TypeEnum.RETROFIT)
    fun provideRetrofit(
        @PublicNetwork(TypeEnum.OKHTTP) okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(RestConfig.LOCAL_URL).callFactory(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapterFactory()).addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class) Json {
                    ignoreUnknownKeys = true;
                    isLenient = true

                }.asConverterFactory("application/json".toMediaType())
            ).build()
    }


    @Provides
    @Singleton
    @PublicNetwork(TypeEnum.SERVICE)
    fun providePublicApiService(
        @PublicNetwork(TypeEnum.RETROFIT) retrofit: Retrofit,
    ): PublicApiService {
        return retrofit.create(PublicApiService::class.java)
    }


}

