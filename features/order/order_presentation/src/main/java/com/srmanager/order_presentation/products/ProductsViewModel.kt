package com.srmanager.order_presentation.products

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.network.dto.Product
import com.srmanager.database.dao.ProductsDao
import com.srmanager.database.entity.ProductsEntity
import com.srmanager.order_domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsDao: ProductsDao,
    private val orderUseCases: OrderUseCases
) : ViewModel() {

    var state by mutableStateOf(ProductsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            productsDao.deleteAll()
            delay(2000)
            launch(Dispatchers.IO) {

                orderUseCases.productsUseCases(customerId = state.customerID).onSuccess {

                    withContext(Dispatchers.Default) {
                        it.products.forEach { item ->
                            productsDao.insertProducts(
                                ProductsEntity(
                                    id = item.id,
                                    title = item.title,
                                    mrpPrice = item.mrpPrice,
                                    wholeSalePrice = item.wholeSalePrice,
                                    lastPurchasePrice = item.lastPurchasePrice,
                                    vatPercentage = item.vatPercentage,
                                    price = item.price,
                                    availableQuantity = item.availableQuantity,
                                    isSelected = false,
                                    selectedItemCount = 1
                                )
                            )
                        }
                    }
                }.onFailure {
                    state = state.copy(isLoading = false)
                }
            }

            launch {
                withContext(Dispatchers.Default) {
                    productsDao.getProducts(key = state.searchKey).collect {
                        Log.d("dataxx", "${it.toString()}")
                        state = state.copy(
                            isLoading = false,
                            searchKey = "",
                            productsList = it.map { product ->
                                Product(
                                    title = product.title,
                                    id = product.id,
                                    mrpPrice = product.mrpPrice,
                                    wholeSalePrice = product.wholeSalePrice,
                                    lastPurchasePrice = product.lastPurchasePrice,
                                    vatPercentage = product.vatPercentage,
                                    price = product.price,
                                    availableQuantity = product.availableQuantity,
                                    isSelected = product.isSelected,
                                    selectedItemCount = product.selectedItemCount
                                )
                            })
                    }

                }
            }


            launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    productsDao.getSelectedItemCount().collect {
                        state = state.copy(isNextButtonEnabled = it > 0)
                    }
                }
            }

        }
    }

    fun onEvent(event: OrderProductsEvent) {
        when (event) {
            is OrderProductsEvent.OnNextEvent -> {

            }

            is OrderProductsEvent.OnIncrementEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(event.id, event.itemCount + 1)
                }
            }

            is OrderProductsEvent.OnDecrementEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(event.id, event.itemCount - 1)
                }
            }

            is OrderProductsEvent.OnItemClickEvent -> {
                viewModelScope.launch(Dispatchers.Default) {

                    productsDao.updateIsSelectedStatus(id = event.id, isSelected = event.isSelected)

                    productsDao.getSelectedItemCount().collect { itemCount ->

                        state = state.copy(isNextButtonEnabled = itemCount > 0)
                    }

                }
            }

            is OrderProductsEvent.OnSearchEvent -> {
                state = state.copy(searchKey = event.key)
                viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Default) {
                        val products = productsDao.getProducts(key = event.key).first()
                        state = state.copy(
                            isLoading = false,
                            productsList = products.map { product ->
                                Product(
                                    title = product.title,
                                    id = product.id,
                                    mrpPrice = product.mrpPrice,
                                    wholeSalePrice = product.wholeSalePrice,
                                    lastPurchasePrice = product.lastPurchasePrice,
                                    vatPercentage = product.vatPercentage,
                                    price = product.price,
                                    availableQuantity = product.availableQuantity,
                                    isSelected = product.isSelected,
                                    selectedItemCount = product.selectedItemCount
                                )
                            })
                    }
                }
            }

            is OrderProductsEvent.OnQuantityInput -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(id = event.id, itemCount = event.qty.toInt())
                }
            }

            is OrderProductsEvent.OnSetOutletID -> {
                state = state.copy(
                    outletID = event.id,
                    customerID = event.customerId
                )
            }
        }
    }
}