package com.srmanager.order_presentation.signature


import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.DATE_FORMAT
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.bitMapToString
import com.srmanager.core.network.dto.Product
import com.srmanager.core.network.model.OrderInformation
import com.srmanager.database.dao.ProductsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
@HiltViewModel
class SignatureViewModel @Inject constructor(private val productsDao: ProductsDao) : ViewModel() {
    var state by mutableStateOf(SignatureState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    productsDao.getSelectedProducts().collect { products ->

                        state = state.copy(productsList = products.map { product ->
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
                                selectedItemCount = product.selectedItemCount,
                                selectedItemTotalPrice = product.selectedItemTotalPrice
                            )

                        }, total = products.sumOf {
                            it.selectedItemTotalPrice
                        })
                    }
                }
            }

            launch(Dispatchers.Main) {
                state = state.copy(
                    orderDate = LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                            DATE_FORMAT
                        )
                    ),
                    orderNo = LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                            "yyMMddhhmmss"
                        )
                    ),
                )
            }
        }
    }

    fun onEvent(event: SignatureEvent) {
        when (event) {
            is SignatureEvent.OnCustomerSignEvent -> {
                state = state.copy(
                    customerSign = bitMapToString(event.value)
                )
            }

            is SignatureEvent.OnSRSignEvent -> {
                state = state.copy(
                    //srSign = bitMapToString(event.value)
                )
            }

            is SignatureEvent.OnDoneEvent -> {

            }

            is SignatureEvent.OnOutletIDEvent-> {
                state = state.copy(
                    outletID = event.value
                )
            }
        }
    }

}