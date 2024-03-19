package com.srmanager.order_data.repository

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.model.CommonErrorModel
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.order_data.dataSource.remote.OrderRemoteDataSource
import com.srmanager.order_data.mapper.toResponse
import com.srmanager.order_domain.model.OrderResponse
import com.srmanager.order_domain.model.ProductsResponse
import com.srmanager.order_domain.repository.OrderRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class OrderRepositoryImpl(
    private val orderRemoteDataSource: OrderRemoteDataSource,
    private val networkHandler: NetworkHandler
) : OrderRepository {
    override suspend fun getProducts(): Result<ProductsResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = orderRemoteDataSource.getProducts()
                Result.success(responseDto.toResponse())
            } catch (e: Exception) {
                val throwable =
                    Throwable(message = e.message)
                Result.failure(throwable)
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun createOrder(orderRequest: OrderRequest): Result<CommonResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = orderRemoteDataSource.createOrder(
                    orderRequest = orderRequest
                )
                Result.success(responseDto.toResponse())
            } catch (e: HttpException) {
                try {
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
                    val throwable =
                        Throwable(message = e.message.toString())
                    Result.failure(throwable)
                }
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }

    override suspend fun fetchOrders(): Result<OrderResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = orderRemoteDataSource.fetchOrders()
                Result.success(responseDto.toResponse())
            } catch (e: HttpException) {
                try {
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
                    val throwable =
                        Throwable(message = e.message.toString())
                    Result.failure(throwable)
                }
            }
        } else {
            val throwable =
                Throwable(message = "No internet connection available")
            Result.failure(throwable)
        }
    }
}