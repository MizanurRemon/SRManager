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
    fun updateIsSelectedStatus(id: Long, isSelected: Boolean)

    @Query("SELECT COUNT(*) from products WHERE isSelected = 1")
    fun getSelectedItemCount(): Int

    @Query("UPDATE products SET selectedItemCount = selectedItemCount + 1.0  WHERE id = :id")
    fun increaseProductItem(id: Long)

    @Query("UPDATE products SET selectedItemCount = selectedItemCount - 1.0  WHERE id = :id")
    fun decreaseProductItem(id: Long)

}