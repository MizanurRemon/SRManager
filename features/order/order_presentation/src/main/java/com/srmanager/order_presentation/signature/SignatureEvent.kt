package com.srmanager.order_presentation.signature


import android.graphics.Bitmap


sealed class SignatureEvent {

    data class OnOutletIDEvent(val value: Int): SignatureEvent()
    data class OnCustomerSignEvent(val value: Bitmap): SignatureEvent()
    data class OnSRSignEvent(val value: Bitmap): SignatureEvent()
    object OnDoneEvent: SignatureEvent()
}