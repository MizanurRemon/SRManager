package com.srmanager.outlet_presentation.outlet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.network.dto.Outlet
import com.srmanager.outlet_domain.model.OutletResponse
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import com.srmanager.outlet_presentation.outlet_add.OutletAddState
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
class OutletViewModel @Inject constructor(private val outletUseCases: OutletUseCases) :
    ViewModel() {

    var state by mutableStateOf(OutletState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var outletList: List<Outlet> = emptyList()

    init {
        getOutletList()
    }

    private fun getOutletList() {

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = true)
            }

            outletUseCases.outletListUseCases().onSuccess {
                outletList = it.data
                withContext(Dispatchers.Main) {
                    state = state.copy(outletList = outletList, isLoading = false, searchKey = "")
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                it.data.size.toString()
                            )
                        )
                    )
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    state = state.copy(isLoading = false)
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

    fun onEvent(event: OutletEvent) {
        when (event) {
            is OutletEvent.OnRefreshEvent -> {
                getOutletList()
            }

            is OutletEvent.OnSearchEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state = state.copy(searchKey = event.value)
                    withContext(Dispatchers.Main) {
                        state = if (event.value.isNotEmpty()) {
                            state.copy(
                                outletList = outletList.filter {
                                    it.outletName.contains(event.value, ignoreCase = true)
                                }
                            )
                        } else {
                            state.copy(outletList = outletList)
                        }
                    }
                }
            }
        }
    }

}

