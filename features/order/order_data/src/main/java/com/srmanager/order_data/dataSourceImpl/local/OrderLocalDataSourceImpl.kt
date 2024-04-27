package com.srmanager.order_data.dataSourceImpl.local

import com.srmanager.database.dao.ProductsDao
import com.srmanager.database.entity.ProductsEntity
import com.srmanager.order_data.dataSource.local.OrderLocalDataSource
import kotlinx.coroutines.flow.Flow

class OrderLocalDataSourceImpl(private val productsDao: ProductsDao) : OrderLocalDataSource {
    override suspend fun insertProducts(productEntity: List<ProductsEntity>) {
        productsDao.insertProducts(productEntity)
    }

    override suspend fun getProducts(key: String): Flow<List<ProductsEntity>> {
        return productsDao.getProducts(key)
    }

    override suspend fun updateIsSelectedStatus(id: Long, isSelected: Boolean) {
       productsDao.updateIsSelectedStatus(id = id, isSelected = isSelected)
    }

    override suspend fun getSelectedItemCount(): Flow<Int> {
        return productsDao.getSelectedItemCount()
    }

    override suspend fun updateProductItem(id: Long, itemCount: Int) {
       productsDao.updateProductItem(id = id, itemCount = itemCount)
    }

    override suspend fun getSelectedProducts(): Flow<List<ProductsEntity>> {
        return productsDao.getSelectedProducts()
    }

    override suspend fun deleteAll() {
        productsDao.deleteAll()
    }
}