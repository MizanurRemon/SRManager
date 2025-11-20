package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.core.designsystem.generatePdf
import com.srmanager.core.network.di.RestConfig
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
    private val orderUseCases: OrderUseCases,
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
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
                    searchedOrderList = it.data.filter { order ->
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

                            val companyID = preferenceDataStoreHelper.getFirstPreference(
                                PreferenceDataStoreConstants.COMPANY_ID, 0
                            )

                            val url = "${RestConfig.LOCAL_URL}/bsol/public/image/$companyID"

                            val bitmap = loadBitmapFromUrl(
                                url
                            )

                            generatePdf(
                                context = event.context,
                                orderDetails = response,
                                headerImage = bitmap
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

    /*private suspend fun loadBitmapFromUrl(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val connection = java.net.URL(url).openConnection()
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            null
        }
    }*/
    private suspend fun loadBitmapFromUrl(
        url: String,
        width: Int = 100,
        height: Int = 100
    ): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val connection = java.net.URL(url).openConnection() as java.net.HttpURLConnection
            connection.apply {
                doInput = true
                connectTimeout = 5000
                readTimeout = 5000
                setRequestProperty("User-Agent", "Mozilla/5.0")
                setRequestProperty("Accept", "*/*")
                connect()
            }

            connection.inputStream.use { input ->
                val originalBitmap = BitmapFactory.decodeStream(input)
                if (originalBitmap == null) return@withContext null

                // Resize to exact 100x100
                originalBitmap
            }
        } catch (e: Exception) {
            Log.d("dataxx", "Failed to load bitmap from URL: $url", e)
            null
        }
    }


}