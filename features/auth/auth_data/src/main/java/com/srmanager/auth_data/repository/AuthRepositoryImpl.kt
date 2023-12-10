package com.srmanager.auth_data.repository

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
              /*  preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.REFRESH_TOKEN,
                    loginResponse.refresh_token
                )

                authLocalDataSource.deleteUsers()
                authLocalDataSource.saveUser(
                    UserEntity(
                        id = loginResponse.userProfile.id,
                        userId = loginResponse.userId,
                        email = loginRequest.userName,
                    )
                )*/
                preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.IS_LOGGED_IN,
                    true
                )
                Result.success(loginResponse)
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    Result.failure(Throwable(e.code().toString()))
                } else {
                    val json =
                        Json { isLenient = true;ignoreUnknownKeys = true }
                    val obj = e.response()?.errorBody()?.string()
                        ?.let {
                            json.decodeFromString<CommonErrorModel>(it)
                        }

                    val message = obj?.error

                    val throwable =
                        Throwable(
                            message = message ?: "Something went wrong",
                            cause = e.cause
                        )
                    Result.failure(throwable)
                }


            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }


    override suspend fun resendVerificationEmail(
        newEmail: String,
        forProfileUpdate: Boolean
    ): Result<CommonResponseDto> {

        return if (networkHandler.isNetworkAvailable()) {
            try {
                val user = authLocalDataSource.getUsers().first()

                val response = authRemoteDataSource.resendVerificationEmail(
                    ResendVerificationRequest(
                        username = user[0].email!!,
                        email = newEmail,// user[0].email
                        forProfileUpdate = forProfileUpdate
                    )
                )
                Result.success(response)
            } catch (e: HttpException) {
                try {
                    val json =
                        Json { isLenient = true;ignoreUnknownKeys = true }
                    val obj = e.response()?.errorBody()?.string()
                        ?.let {
                            json.decodeFromString<CommonErrorModel>(it)
                        }
                    val message = obj?.error?.let { it }

                    val throwable =
                        Throwable(message = message ?: "Something went wrong", cause = e.cause)
                    Result.failure(throwable)
                } catch (e: Exception) {
                    val throwable =
                        Throwable(message = "Something went wrong")
                    Result.failure(throwable)
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }

    }

    override suspend fun updateEmail(email: String): Result<LoginResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {

                val user = authLocalDataSource.getUsers().first()
                val loginDto = authRemoteDataSource.updateEmail(
                    UpdateEmailRequest(
                        newEmail = email,
                        userId = user[0].userId!!
                    )
                )

                val loginResponse = loginDto.toLoginResponse()
                preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.ACCESS_TOKEN,
                    loginResponse.data
                )
            /*    preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.REFRESH_TOKEN,
                    loginResponse.refresh_token
                )

                authLocalDataSource.deleteUsers()
                authLocalDataSource.saveUser(
                    UserEntity(
                        id = loginResponse.userProfile.id,
                        userId = loginResponse.userId,
                        email = email,
                    )
                )*/
                preferenceDataStoreHelper.putPreference(
                    PreferenceDataStoreConstants.IS_LOGGED_IN,
                    true
                )
                Result.success(loginResponse)


            } catch (e: Exception) {
                val throwable =
                    Throwable(message = "Something went wrong")
                Result.failure(throwable)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }
}