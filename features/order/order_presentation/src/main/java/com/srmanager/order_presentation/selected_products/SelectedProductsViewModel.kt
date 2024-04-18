package com.srmanager.order_presentation.selected_products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.network.dto.Product
import com.srmanager.database.dao.ProductsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SelectedProductsViewModel @Inject constructor(private val productsDao: ProductsDao) :
    ViewModel() {
    var state by mutableStateOf(SelectedProductsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {

        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                productsDao.getSelectedProducts().collect {
                    state = state.copy(
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
                        }
                    )
                }
            }
        }
    }


    fun onEvent(event: SelectedProductEvent) {
        when (event) {
            is SelectedProductEvent.OnSubmitEvent -> {

            }

            is SelectedProductEvent.OnIncrementEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(event.id , event.selectedItemCount+1)
                }
            }

            is SelectedProductEvent.OnDecrementEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    productsDao.updateProductItem(event.id, event.selectedItemCount-1)
                }
            }
        }
    }
}