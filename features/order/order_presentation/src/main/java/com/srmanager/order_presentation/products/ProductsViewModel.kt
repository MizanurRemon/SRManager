package com.srmanager.order_presentation.products

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.order_domain.model.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(ProductsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(2000)
            state = state.copy(isLoading = false, productsList = productList)
        }
    }

    fun onEvent(event: OrderProductsEvent) {
        when (event) {
            is OrderProductsEvent.OnNextEvent -> {

            }

            is OrderProductsEvent.OnIncrementEvent -> {
                Log.d("dataxx", "onEvent: ${event.index}")
                productList[event.index].selectedItemCount++
            }

            is OrderProductsEvent.OnDecrementEvent -> {

            }
        }
    }
}



var productList = listOf(
    Products(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    ),
    Products(
        id = 2,
        title = "Mens Casual Premium Slim Fit T-Shirts",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
    ),
    Products(
        id = 3,
        title = "Mens Cotton Jacket",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
    ),
    Products(
        id = 4,
        title = "Mens Casual Slim Fit",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"
    ),
    Products(
        id = 5,
        title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"
    ),
    Products(
        id = 7,
        title = "White Gold Plated Princess",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg"
    ),
    Products(
        id = 8,
        title = "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg",
    ),
    Products(
        id = 9,
        title = "Solid Gold Petite Micropave",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg",
    ),
    Products(
        id = 10,
        title = "Silicon Power 256GB SSD 3D NAND A55 SLC Cache Performance Boost SATA III 2.5",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg",
    ),
    Products(
        id = 11,
        title = "WD 4TB Gaming Drive Works with Playstation 4 Portable External Hard Drive",
        stock = 25,
        price = 75.25,
        unit = "piece",
        image = "https://fakestoreapi.com/img/61mtL65D4cL._AC_SX679_.jpg",
    )
)