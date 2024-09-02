package com.srmanager.auth_data.repository

import android.util.Log
import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.auth_data.dataSource.local.AuthLocalDataSource
import com.srmanager.auth_data.dataSource.remote.AuthRemoteDataSource
import com.srmanager.auth_data.mapper.toLoginResponse
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.*
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.database.entity.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException


class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
    private val networkHandler: NetworkHandler,
) : AuthRepository {
    override suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val loginDto = authRemoteDataSource.getLoginResponse(loginRequest)

                val loginResponse = loginDto.toLoginResponse()
                preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.ACCESS_TOKEN,
                    loginResponse.data
                )

                preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.IS_LOGGED_IN,
                    true
                )
                Result.success(loginResponse)
            } catch (e: HttpException) {
                val json =
                    Json { isLenient = true;ignoreUnknownKeys = true }
                val obj = e.response()?.errorBody()?.string()
                    ?.let {
                        json.decodeFromString<CommonErrorModel>(it)
                    }

                val message = obj?.message

                val throwable =
                    Throwable(
                        message = message ?: "Something went wrong",
                        cause = e.cause
                    )
                Result.failure(throwable)


            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }
}