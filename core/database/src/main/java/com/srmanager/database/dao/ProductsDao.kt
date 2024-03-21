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

    @Query("SELECT * from products WHERE title LIKE '%' || :key ||'%'")
    fun getProducts(key : String): Flow<List<ProductsEntity>>

    @Query("UPDATE products SET isSelected= :isSelected, selectedItemTotalPrice = selectedItemCount * mrpPrice WHERE id = :id")
    fun updateIsSelectedStatus(id: Long, isSelected: Boolean)

    @Query("SELECT COUNT(*) from products WHERE isSelected = 1")
    fun getSelectedItemCount(): Flow<Int>

    @Query("UPDATE products SET selectedItemCount = selectedItemCount + 1.0, selectedItemTotalPrice = selectedItemCount * mrpPrice  WHERE id = :id")
    fun increaseProductItem(id: Long)

    @Query("UPDATE products SET selectedItemCount = selectedItemCount - 1.0, selectedItemTotalPrice = selectedItemCount * mrpPrice  WHERE id = :id")
    fun decreaseProductItem(id: Long)


    @Query("SELECT * from products WHERE isSelected")
    fun getSelectedProducts(): Flow<List<ProductsEntity>>

    @Query("UPDATE products SET selectedItemCount =:qty, selectedItemTotalPrice = selectedItemCount * mrpPrice  WHERE id = :id")
    fun updateProductItem(id: Long, qty: Double)

    @Query("DELETE FROm products")
    fun deleteAll()

}