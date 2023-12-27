package com.srmanager.app.permission

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccessibilityViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    val isUserConsent = mutableStateOf(true)
    fun checkConsent() {
        /*viewModelScope.launch(Dispatchers.IO) {
            userDao.getUsers().collect {

                if (it.isNotEmpty()){
                    isUserConsent.value = !it[0].isUserGiveConsent
                }
            }
        }*/

    }

    fun updateConsent() {
        viewModelScope.launch(Dispatchers.IO) {
            //userDao.updateUserConsent(true)
        }
    }
}