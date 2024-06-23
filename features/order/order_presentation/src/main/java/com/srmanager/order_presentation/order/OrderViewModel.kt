package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.designsystem.generatePDF
import com.srmanager.core.designsystem.generatePDF2
import com.srmanager.order_domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("SimpleDateFormat")
@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(OrderState())
        private set

    init {
        /* state = state.copy(
             startDate = SimpleDateFormat(DATE_FORMAT).format(Date()),
             endDate = SimpleDateFormat(DATE_FORMAT).format(Date())

         )*/
        GlobalScope.launch {
            delay(1000)
        }
        getOrderItem()
    }

    private fun getOrderItem() {

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            orderUseCases.orderFetchUseCases().onSuccess {
                state = state.copy(
                    isLoading = false,
                    orderList = it.data,
                    searchedOrderList = it.data.filter {order->
                        order.orderDate.contains(state.searchText)
                    }
                )
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
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.OnOrderCodeClickEvent -> {
                viewModelScope.launch {
                    state = state.copy(
                        isLoading = true
                    )
                    orderUseCases.orderDetailsUseCase(orderID = event.id)
                        .onSuccess { response ->
                            state = state.copy(
                                isLoading = false,
                                orderDetails = response
                            )
                            generatePDF(event.context, response)
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
                }
            }

            is OrderEvent.OnSearchEvent -> {
                viewModelScope.launch {
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            searchText = event.key
                        )

                        if (event.key.length > 3) {
                            state = state.copy(
                                searchedOrderList = state.orderList.filter {
                                    it.outletName.contains(
                                        event.key,
                                        ignoreCase = true
                                    ) || it.orderNo.contains(
                                        event.key,
                                        ignoreCase = true
                                    ) || it.orderDate.contains(event.key, ignoreCase = true)
                                }
                            )

                        }

                        if (event.key.isEmpty()) {
                            state = state.copy(
                                searchedOrderList = state.orderList
                            )
                        }
                    }
                }
            }

            is OrderEvent.OnCalenderTapEvent -> {
                state = state.copy(
                    showCalenderDialog = true
                )
            }
        }
    }

}