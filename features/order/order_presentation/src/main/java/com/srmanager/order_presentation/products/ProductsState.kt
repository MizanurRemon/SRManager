package com.srmanager.order_presentation.products

import com.srmanager.core.network.dto.Product

data class ProductsState(
    var productsList: List<Product> = emptyList(),
    var searchedProductList: List<Product> = emptyList(),
    var isLoading: Boolean = false,
    var isNextButtonEnabled: Boolean = false,
    var searchKey : String = "",
    var outletID : String = "",
    var customerID: String = "",
    var isInputNumberValid: Boolean = false,
    var tappedID: Int = 0,
    val showTextFieldValueUpdateDialog : Boolean = false
)