package com.srmanager.outlet_presentation.maps.Multiple

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.database.dao.LocationDao
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllOutletMapViewModel @Inject constructor(
    private val outletUseCases: OutletUseCases,
    private var locationDao: LocationDao
) : ViewModel() {

    var state by mutableStateOf(OutletMapState())
        private set

    init {
        viewModelScope.launch {
            launch {
                state = state.copy(isLoading = true)
                outletUseCases.outletListUseCases().onSuccess {
                    state = state.copy(isLoading = false, outletList = it.data)
                }.onFailure {
                    state = state.copy(isLoading = false, isFailed = true)
                }
            }

            launch {
                locationDao.getLocation().collect {
                    if (it.isNotEmpty()) {
                        state = state.copy(
                            myLatitude = it[0].latitude!!.toDouble(),
                            myLongitude = it[0].longitude!!.toDouble(),
                            isLocationFetched = true
                        )
                    }
                }
            }
        }
    }

}