package com.srmanager.outlet_presentation.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.REPORT_SUBJECT_LIST
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.ColorTextPrimary
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.boldBodyTextStyle
import com.srmanager.core.designsystem.w
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutletCheckoutScreen(onBack: () -> Unit, viewModel: OutletCheckOutViewModel = hiltViewModel()) {

    var selectSubjectWarning by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    var isDescriptionBlank by remember {
        mutableStateOf(false)
    }

    var subjectItem by remember {
        mutableIntStateOf(CommonR.string.done)
    }

    var subjectClicked by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.outlet_checkout
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.r())
                .verticalScroll(rememberScrollState()),

            ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        subjectClicked = !subjectClicked
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ), border = BorderStroke(
                    width = 1.dp, color = if (selectSubjectWarning) {
                        Color.Red
                    } else {
                        Color.Transparent
                    }
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.w()),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = subjectItem),
                        style = if (subjectItem == CommonR.string.done) {
                            bodyRegularTextStyle.copy(
                                color = if (selectSubjectWarning) {
                                    Color.Red
                                } else {
                                    ColorTextFieldPlaceholder
                                }
                            )
                        } else {
                            bodyRegularTextStyle.copy(
                                color = ColorTextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = if (isDescriptionBlank) Color.Red else Color.White,
                        unfocusedBorderColor = if (isDescriptionBlank) Color.Red else Color.White,
                        cursorColor = Color.Black,
                    ),
                    value = viewModel.state.description,
                    onValueChange = {
                        viewModel.onEvent(OutletCheckOutEvent.OnRemarksEnter(it))
                        isDescriptionBlank = it.isEmpty()
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = CommonR.string.remarks),
                            style = bodyRegularTextStyle.copy(color = if (isDescriptionBlank) Color.Red else ColorTextFieldPlaceholder)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(top = 10.dp)
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(15.dp))
                )

                if (subjectClicked) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.r())
                            .clickable {
                                subjectClicked = false
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE0F1E6),
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                    ) {
                        repeat(REPORT_SUBJECT_LIST.size) { index ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        subjectItem = REPORT_SUBJECT_LIST[index]
                                        subjectClicked = false
                                        selectSubjectWarning = false
                                        viewModel.onEvent(
                                            OutletCheckOutEvent.OnReasonSelect(
                                                context.resources.getString(
                                                    REPORT_SUBJECT_LIST[index]
                                                )
                                            )
                                        )
                                    }
                            ) {
                                Text(
                                    text = stringResource(id = REPORT_SUBJECT_LIST[index]),
                                    style = bodyRegularTextStyle.copy(
                                        color = ColorTextPrimary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier
                                        .padding(15.dp)
                                )

                                if (index != REPORT_SUBJECT_LIST.size - 1) {
                                    Divider(
                                        color = Color.LightGray,
                                        thickness = 1.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.r()))

            Row() {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = CommonR.string.remaining) + ":" + viewModel.state.remainingWords,
                    textAlign = TextAlign.Center,
                    style = bodyRegularTextStyle.copy(color = Color.LightGray, fontSize = 14.sp)
                )
            }
            Spacer(modifier = Modifier.height(20.r()))

            Row {
                Text(text = stringResource(id = CommonR.string.location))
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${viewModel.state.latitude}, ${viewModel.state.longitude}",
                    style = boldBodyTextStyle.copy(color = Color.Black, fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(50.r()))

            AppActionButtonCompose(stringId = CommonR.string.done, onActionButtonClick = {})
        }
    }

}

@Composable
@Preview
fun PreviewOutletCheckoutScreen() {
    OutletCheckoutScreen(onBack = {})
}
