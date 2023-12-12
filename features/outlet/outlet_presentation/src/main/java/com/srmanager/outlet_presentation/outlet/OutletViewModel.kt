package com.srmanager.outlet_presentation.outlet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.outlet_domain.model.OutletResponse
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import com.srmanager.outlet_presentation.outlet_add.OutletAddState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OutletViewModel @Inject constructor(private val outletUseCases: OutletUseCases) :
    ViewModel() {

    var state by mutableStateOf(OutletState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        getOutletList()
    }

    private fun getOutletList() {

        viewModelScope.launch {
            state = state.copy(isLoading = true)

            outletUseCases.outletListUseCases().onSuccess {
                state = state.copy(outletList = it, isLoading = false)
            }.onFailure {
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

