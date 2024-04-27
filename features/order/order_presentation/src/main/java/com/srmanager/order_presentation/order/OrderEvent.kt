package com.srmanager.order_presentation.order

import android.content.Context

sealed class OrderEvent {
   data class OnOrderCodeClickEvent(val id: String, val context : Context) : OrderEvent()
}