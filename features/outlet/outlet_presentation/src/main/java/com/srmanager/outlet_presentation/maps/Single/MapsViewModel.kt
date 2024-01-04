package com.srmanager.outlet_presentation.maps.Single

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.database.dao.LocationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapsViewModel @Inject constructor(private var locationDao: LocationDao): ViewModel(){

    var state by mutableStateOf(MapsState())
        private set
    init {
        viewModelScope.launch {
            launch {
                locationDao.getLocation().collect {
                    if (it.isNotEmpty()) {
                        state = state.copy(
                            latitude = it[0].latitude!!.toDouble(),
                            longitude = it[0].longitude!!.toDouble()
                        )
                    }
                }
            }
        }
    }

}