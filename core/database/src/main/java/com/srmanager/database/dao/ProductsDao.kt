package com.srmanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.srmanager.database.entity.ProductsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(productEntity: ProductsEntity)

    @Query("SELECT * from products")
    fun getProducts(): Flow<List<ProductsEntity>>

    @Query("UPDATE products SET isSelected= :isSelected WHERE id = :id")
    fun updateIsSelectedStatus(id: Int, isSelected: Boolean)

    @Query("SELECT COUNT(*) from products WHERE isSelected = 1")
    fun getSelectedItemCount(): Int

}