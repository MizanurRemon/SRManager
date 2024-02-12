package com.srmanager.order_domain.model

data class Products(
    var id: Int,
    var title: String,
    var stock: Int,
    var price: Double,
    var unit: String,
    var image: String,
    var selectedItemCount : Int = 1
)
