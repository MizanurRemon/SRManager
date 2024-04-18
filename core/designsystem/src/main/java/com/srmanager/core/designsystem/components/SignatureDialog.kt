package com.srmanager.core.designsystem.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.srmanager.core.common.R
import com.srmanager.core.designsystem.PathState
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.AppCancelButtonCompose
import se.warting.signaturepad.SignaturePadAdapter
import se.warting.signaturepad.SignaturePadView

@ExperimentalComposeUiApi
@Composable
fun SignatureDialog(
    isDialogOpen: MutableState<Boolean>,
    onSave: (image: Bitmap) -> Unit
) {
    var signaturePadAdapter: SignaturePadAdapter? = null
    if (isDialogOpen.value) {
        Dialog(
            onDismissRequest = { isDialogOpen.value = true }, //outside click listener set false
            properties = DialogProperties(usePlatformDefaultWidth = false) // set Dialog fullView
        ) {
            val view = LocalView.current //get current view
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.r())
                    .background(color = Color.White, shape = RoundedCornerShape(10.r()))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.r())
                        .padding(20.r())
                        .clip(RoundedCornerShape(10.r()))
                        .border(
                            width = 1.r(),
                            color = Color.Black,
                            shape = RoundedCornerShape(10.r())
                        ),
                ) {

                    SignaturePadView(
                        onReady = {
                            signaturePadAdapter = it
                        }, modifier = Modifier
                            .fillMaxSize()

                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.r()).padding(bottom = 20.r()),
                ) {


                    AppCancelButtonCompose(
                        titleStringResId = R.string.clear,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.r())
                    ) {
                        signaturePadAdapter?.clear()
                    }

                    Spacer(
                        modifier = Modifier
                            .width(20.r())
                    )

                    AppActionButtonCompose(
                        stringId = R.string.save,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.r())
                    ) {
                        isDialogOpen.value = false
                        onSave(signaturePadAdapter?.getTransparentSignatureBitmap()!!)
                    }


                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun PreviewSignatureDialog() {
    val isDialogOpen = remember { mutableStateOf(true) }

    SignatureDialog(
        isDialogOpen = isDialogOpen,
        onSave = {

        }
    )
}