package com.srmanager.order_presentation.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.NUMBER_REGEX
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.network.dto.Product
import com.srmanager.database.dao.ProductsDao
import com.srmanager.order_domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
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
                    /*state = state.copy(
                        isLoading = false,
                        productsList = productsDao.getProducts(state.searchKey).first()
                            .map { product ->
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
                            },
                        searchedProductList = productsDao.getProducts(state.searchKey).first()
                            .map { product ->
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
                            },
                    )*/
                }.onFailure {
                    state = state.copy(isLoading = false)
                }
            }

            launch {
                withContext(Dispatchers.Default) {
                    productsDao.getProducts(key = "").collect {
                        state = state.copy(
                            searchKey = "",
                            isLoading = false,
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

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.OnNextEvent -> {

            }

            is ProductsEvent.OnIncrementEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(event.id, event.itemCount + 1)
                }
            }

            is ProductsEvent.OnDecrementEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(event.id, event.itemCount - 1)
                }
            }

            is ProductsEvent.OnItemClickEvent -> {
                viewModelScope.launch(Dispatchers.Default) {

                    productsDao.updateIsSelectedStatus(id = event.id, isSelected = event.isSelected)

                    productsDao.getSelectedItemCount().collect { itemCount ->

                        state = state.copy(isNextButtonEnabled = itemCount > 0)
                    }

                }
            }

            is ProductsEvent.OnSearchEvent -> {

                viewModelScope.launch {
                    state = state.copy(searchKey = event.key)
                    /* state = if (event.key.isNotEmpty() && event.key.length > 2) {
                         state.copy(
                             searchedProductList = state.productsList.filter {
                                 it.title.lowercase(Locale.ROOT).contains(
                                     event.key.lowercase(
                                         Locale.ROOT
                                     )
                                 )
                             }
                         )
                     } else {
                         state.copy(searchedProductList = state.productsList)
                     }*/

                    withContext(Dispatchers.Default) {
                        productsDao.getProducts(key = event.key).collect {
                            state = state.copy(
                                //searchKey = "",
                                isLoading = false,
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
            }

            is ProductsEvent.OnQuantityInput -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItemFromInput(
                        id = event.id,
                        itemCount = event.qty.toInt(),
                        isSelected = true
                    )
                }
            }

            is ProductsEvent.OnSetOutletID -> {
                state = state.copy(
                    outletID = event.id,
                    customerID = event.customerId
                )
            }

            is ProductsEvent.OnNumberInputEvent -> {
                state = state.copy(
                    isInputNumberValid = event.value.matches(NUMBER_REGEX.toRegex())
                )
            }
        }
    }
}