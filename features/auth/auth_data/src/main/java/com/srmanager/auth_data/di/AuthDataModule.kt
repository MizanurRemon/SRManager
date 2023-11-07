package com.srmanager.auth_data.di

import android.content.Context
import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.auth_data.dataSource.local.AuthLocalDataSource
import com.srmanager.auth_data.dataSource.remote.AuthRemoteDataSource
import com.srmanager.auth_data.dataSourceImpl.local.AuthLocalDataSourceImpl
import com.srmanager.auth_data.dataSourceImpl.remote.AuthRemoteDataSourceImpl
import com.srmanager.auth_data.repository.AuthRepositoryImpl
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.di.TypeEnum
import com.srmanager.core.network.di.qualifier.PrivateNetwork
import com.srmanager.core.network.di.qualifier.PublicNetwork
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AuthDataModule {

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(
        @PublicNetwork(TypeEnum.SERVICE) publicApiService: PublicApiService,
        @PrivateNetwork(TypeEnum.SERVICE) privateApiService: PrivateApiService,
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(publicApiService, privateApiService)
    }

    @Singleton
    @Provides
    fun provideAuthLocalDataSource(
        userDao: UserDao,
    ): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(
        @ApplicationContext context: Context,
    ): NetworkHandler {
        return NetworkHandler(context)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource,
        preferenceDataStoreHelper: PreferenceDataStoreHelper,
        networkHandler: NetworkHandler,
    ): AuthRepository {
        return AuthRepositoryImpl(
            authRemoteDataSource, authLocalDataSource, preferenceDataStoreHelper, networkHandler
        )
    }
}