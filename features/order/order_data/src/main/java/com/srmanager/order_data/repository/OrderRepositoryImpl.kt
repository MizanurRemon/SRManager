package com.srmanager.order_data.repository

import android.util.Log
import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.model.CommonErrorModel
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.database.entity.ProductsEntity
import com.srmanager.order_data.dataSource.local.OrderLocalDataSource
import com.srmanager.order_data.dataSource.remote.OrderRemoteDataSource
import com.srmanager.order_data.mapper.toResponse
import com.srmanager.order_domain.model.OrderDetailsResponse
import com.srmanager.order_domain.model.OrderResponse
import com.srmanager.order_domain.model.ProductsResponse
import com.srmanager.order_domain.repository.OrderRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class OrderRepositoryImpl(
    private val orderRemoteDataSource: OrderRemoteDataSource,
    private val networkHandler: NetworkHandler,
    private val orderLocalDataSource: OrderLocalDataSource
) : OrderRepository {
    override suspend fun getProducts(customerId: String): Result<ProductsResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = orderRemoteDataSource.getProducts(customerId = customerId)

                orderLocalDataSource.insertProducts(
                    productEntity = responseDto.data.map { item ->
                        ProductsEntity(
                            id = item.id,
                            title = item.title,
                            mrpPrice = item.mrpPrice,
                            wholeSalePrice = item.wholeSalePrice,
                            lastPurchasePrice = item.lastPurchasePrice,
                            vatPercentage = item.vatPercentage,
                            price = item.price,
                            availableQuantity = item.availableQuantity,
                            isSelected = false,
                            selectedItemCount = 1
                        )
                    }
                )
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

    override suspend fun orderDetails(orderId: String): Result<OrderDetailsResponse> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val responseDto = orderRemoteDataSource.orderDetails(orderId)
                val response = responseDto.toResponse()

                Result.success(response)
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