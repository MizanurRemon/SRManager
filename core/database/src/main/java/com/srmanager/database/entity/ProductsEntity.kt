package com.srmanager.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val mrpPrice: Double,
    val wholeSalePrice: Double,
    val lastPurchasePrice: Double,
    val vatPercentage: Double,
    val price: Double,
    val availableQuantity: Double,
    var isSelected: Boolean,
    var selectedItemCount: Int
)
