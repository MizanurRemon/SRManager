package com.srmanager.order_data.dataSource.local

import com.srmanager.database.entity.LocationEntity
import com.srmanager.database.entity.ProductsEntity
import kotlinx.coroutines.flow.Flow

interface OrderLocalDataSource {
    suspend fun insertProducts(productEntity: List<ProductsEntity>)
    suspend fun getProducts(key: String): Flow<List<ProductsEntity>>
    suspend fun updateIsSelectedStatus(id: Long, isSelected: Boolean)
    suspend fun getSelectedItemCount(): Flow<Int>
    suspend fun updateProductItem(id: Long, itemCount: Int)
    suspend fun getSelectedProducts(): Flow<List<ProductsEntity>>
    suspend fun deleteAll()
    suspend fun getLocation(): Flow<List<LocationEntity>>
}