package com.srmanager.outlet_data.repository

import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.CheckOutRequest
import com.srmanager.outlet_domain.repository.OutletRepository
import com.srmanager.outlet_data.dataSource.remote.OutletRemoteDataSource
import com.srmanager.outlet_data.mapper.toResponse
import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.outlet_domain.model.CheckOutStatusResponse

import com.srmanager.outlet_domain.model.CommonResponse
import com.srmanager.outlet_domain.model.MarketResponse
import com.srmanager.outlet_domain.model.OutletDetailsResponse
import com.srmanager.outlet_domain.model.OutletResponse


class OutletRepositoryImpl(
    private val outletRemoteDataSource: OutletRemoteDataSource,
    private val networkHandler: NetworkHandler,
) : OutletRepository {
    override suspend fun addOutlet(outletAddRequest: OutletAddRequest): Result<CommonResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = outletRemoteDataSource.addOutlet(outletAddRequest)

                val response = responseDto.toResponse()

                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun getOutletList(): Result<OutletResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = outletRemoteDataSource.getOutletList()

                val response = responseDto.toResponse()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun getOutletDetails(outletID: String): Result<OutletDetailsResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = outletRemoteDataSource.getOutletDetails(outletID = outletID)
                val response = responseDto.toResponse()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun getCheckOutStatusList(): Result<CheckOutStatusResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = outletRemoteDataSource.getCheckOutStatusList()
                val response = responseDto.toResponse()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun checkout(checkOutRequest: CheckOutRequest): Result<CommonResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = outletRemoteDataSource.checkout(checkOutRequest)
                val response = responseDto.toResponse()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun getMarkets(): Result<MarketResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = outletRemoteDataSource.getMarkets()
                val response = responseDto.toResponse()
                Result.success(response)
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