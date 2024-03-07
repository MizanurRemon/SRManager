package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class ProductsDto(
    val data: ArrayList<Product> = arrayListOf()
)


@Serializable
data class Product(
    val title: String,
    val id: Long,
    val mrpPrice: Double,
    val wholeSalePrice: Double,
    val lastPurchasePrice: Double,
    val vatPercentage: Double,
    val price: Double,
    val availableQuantity: Double,
    val selectedItemCount: Int = 1,
    val isSelected : Boolean = false,
    val selectedItemTotalPrice : Double = 0.0
)
