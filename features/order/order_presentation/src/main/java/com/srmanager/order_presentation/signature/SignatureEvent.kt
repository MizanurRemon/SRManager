package com.srmanager.order_presentation.signature


import android.content.Context
import android.graphics.Bitmap
import com.srmanager.core.network.dto.Outlet


sealed class SignatureEvent {

    data class OnOutletDetailsEvent(
        val outletDetails : Outlet
    ) : SignatureEvent()
    data class OnCustomerSignEvent(val value: Bitmap) : SignatureEvent()
    object OnDoneEvent : SignatureEvent()
    data class OnPdfGenerate(val context: Context) : SignatureEvent()
}