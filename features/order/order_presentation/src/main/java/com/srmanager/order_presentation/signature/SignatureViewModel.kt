package com.srmanager.order_presentation.signature


import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.MINIMUM_DISTANCE_FOR_CHECKOUT
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.common.util.bitMapToString
import com.srmanager.core.common.util.calculationDistance
import com.srmanager.core.designsystem.generatePDF
import com.srmanager.core.network.dto.Product
import com.srmanager.core.network.model.OrderDetail
import com.srmanager.core.network.model.OrderInformation
import com.srmanager.database.dao.LocationDao
import com.srmanager.database.dao.ProductsDao
import com.srmanager.order_domain.model.CreateOrderModel
import com.srmanager.order_domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
@HiltViewModel
class SignatureViewModel @Inject constructor(
    private val productsDao: ProductsDao,
    private val orderUseCases: OrderUseCases,
    private val locationDao: LocationDao
) : ViewModel() {
    var state by mutableStateOf(SignatureState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch(Dispatchers.Default) {
                try {

                    val products = productsDao.getSelectedProducts().first()
                    val newProductsList = products.map { product ->
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
                            selectedItemTotalPrice = String.format(
                                "%.2f",
                                product.selectedItemTotalPrice
                            ).toDouble()
                        )
                    }
                    val newTotal = products.sumOf {
                        it.selectedItemTotalPrice
                    }

                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            productsList = newProductsList,
                            total = String.format("%.2f", newTotal).toDouble()
                        )
                    }

                } catch (_: Exception) {

                }
            }

            launch(Dispatchers.Main) {
                state = state.copy(
                    orderDate = LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd HH:mm:ss"
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

            is SignatureEvent.OnDoneEvent -> {
                viewModelScope.launch {

                    val myLocationDetails = locationDao.getLocation().first()
                    state = state.copy(
                        isLoading = true,
                        isOrderReady = state.customerSign.isNotEmpty() && state.outlet.id != 0
                                && state.orderDate.isNotEmpty() && state.total != 0.0,
                        isDistanceValid = calculationDistance(
                            latitude = state.outlet.latitude,
                            longitude = state.outlet.longitude,
                            myLatitude = myLocationDetails[0].latitude.toString(),
                            myLongitude = myLocationDetails[0].longitude.toString()
                        ) <= MINIMUM_DISTANCE_FOR_CHECKOUT
                    )



                    when {
                        state.isDistanceValid -> {
                            when {
                                state.isOrderReady -> {
                                    state = state.copy(
                                        isLoading = false,
                                        orderSuccessDialog = false
                                    )
                                    orderUseCases.createOrderUseCases(
                                        CreateOrderModel(
                                            orderInformation = OrderInformation(
                                                customerSignature = state.customerSign,
                                                outletId = state.outlet.id.toLong(),
                                                orderDate = state.orderDate,
                                                totalAmount = state.total,
                                                contactNo = state.outlet.mobileNo
                                            ),
                                            orderDetails = state.productsList.map { product ->
                                                OrderDetail(
                                                    productId = product.id,
                                                    quantity = product.selectedItemCount.toLong(),
                                                    mrp = product.mrpPrice,
                                                    totalProductAmount = product.selectedItemTotalPrice,
                                                )
                                            }
                                        )
                                    ).onSuccess {
                                        state = state.copy(
                                            isLoading = false,
                                            orderSuccessDialog = true
                                        )

                                        orderUseCases.orderDetailsUseCase(orderID = it.orderId.toString())
                                            .onSuccess { response ->
                                                state = state.copy(
                                                    isLoading = false,
                                                    orderSuccessDialog = true,
                                                    orderDetails = response
                                                )
                                            }.onFailure { error ->
                                                state = state.copy(
                                                    isLoading = false
                                                )

                                                _uiEvent.send(
                                                    UiEvent.ShowSnackbar(
                                                        UiText.DynamicString(
                                                            error.message.toString()
                                                        )
                                                    )
                                                )
                                            }


                                    }.onFailure {
                                        state = state.copy(
                                            isLoading = false
                                        )

                                        _uiEvent.send(
                                            UiEvent.ShowSnackbar(
                                                UiText.DynamicString(
                                                    it.message.toString()
                                                )
                                            )
                                        )
                                    }
                                }

                                else -> {
                                    state = state.copy(
                                        isLoading = false
                                    )

                                    _uiEvent.send(
                                        UiEvent.ShowSnackbar(
                                            UiText.DynamicString(
                                                "can't create order, missing data"
                                            )
                                        )
                                    )
                                }
                            }
                        }

                        else -> {
                            state = state.copy(
                                isLoading = false
                            )
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        "You are more than ${MINIMUM_DISTANCE_FOR_CHECKOUT}m away"
                                    )
                                )
                            )
                        }
                    }
                }
            }

            is SignatureEvent.OnOutletDetailsEvent -> {
                state = state.copy(
                    outlet = event.outletDetails
                )
            }

            is SignatureEvent.OnPdfGenerate -> {
                generatePDF(event.context, state.orderDetails)
            }
        }
    }

}