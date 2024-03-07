package com.srmanager.order_presentation.signature

import com.srmanager.core.network.dto.Product

data class SignatureState(
    val customerSign : String = "",
    /*val srSign: String = ""*/
    var productsList: List<Product> = arrayListOf(),
    val total :Double = 0.0,
    val orderDate: String = ""
)
