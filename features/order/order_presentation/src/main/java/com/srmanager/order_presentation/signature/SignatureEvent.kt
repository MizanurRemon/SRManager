package com.srmanager.order_presentation.signature


import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.platform.ComposeView


sealed class SignatureEvent {

    data class OnOutletDetailsEvent(val id: Int, val contactNo: String) : SignatureEvent()
    data class OnCustomerSignEvent(val value: Bitmap) : SignatureEvent()
    object OnDoneEvent : SignatureEvent()
    data class OnPdfGenerate(val context: Context, val compose: Unit) : SignatureEvent()
}