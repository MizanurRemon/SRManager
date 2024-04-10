package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.DATE_FORMAT
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.order_domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

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
        state = state.copy(
            startDate = SimpleDateFormat(DATE_FORMAT).format(Date()),
            endDate = SimpleDateFormat(DATE_FORMAT).format(Date())

        )
        getOrderItem()
    }

    private fun getOrderItem() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            orderUseCases.orderFetchUseCases().onSuccess {
                state = state.copy(
                    isLoading = false,
                    orderList = it.data
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

}