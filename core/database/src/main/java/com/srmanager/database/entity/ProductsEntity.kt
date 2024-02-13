package com.srmanager.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var stock: Int,
    var price: Double,
    var unit: String,
    var image: String,
    var isSelected: Boolean,
    var selectedItemCount: Int
)
