package com.srmanager.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.srmanager.database.util.TypeConverterHelper
import java.math.BigDecimal
import java.math.RoundingMode


@Entity(tableName = "products")
@TypeConverters(TypeConverterHelper::class)
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
    var selectedItemCount: Int,
    var selectedItemTotalPrice: Double = 0.0
){
    fun formatTotalPrice(): String {
        return String.format("%.2f", selectedItemTotalPrice)
    }

    fun formatTotalPriceUsingBigDecimal(): String {
        val bd = BigDecimal(selectedItemTotalPrice).setScale(2, RoundingMode.HALF_UP)
        return bd.toString()
    }
}
