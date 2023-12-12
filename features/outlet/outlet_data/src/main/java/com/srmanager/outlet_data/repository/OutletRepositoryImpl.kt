package com.srmanager.outlet_data.repository

import com.srmanager.outlet_domain.repository.OutletRepository
import com.srmanager.outlet_data.dataSource.remote.OutletRemoteDataSource
import com.srmanager.outlet_data.mapper.toResponse
import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.core.network.util.NetworkHandler

import com.srmanager.outlet_domain.model.CommonResponse
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
}