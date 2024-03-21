package com.srmanager.order_presentation.signature


import android.graphics.Bitmap


sealed class SignatureEvent {

    data class OnOutletDetailsEvent(val id: Int, val contactNo : String): SignatureEvent()
    data class OnCustomerSignEvent(val value: Bitmap): SignatureEvent()
    object OnDoneEvent: SignatureEvent()
}