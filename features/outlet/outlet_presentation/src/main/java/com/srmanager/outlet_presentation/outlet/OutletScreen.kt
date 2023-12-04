package com.srmanager.outlet_presentation.outlet

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyRegularSpanStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.outlet_domain.model.OutletResponse
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OutletScreen(
    onBack: () -> Unit,
    viewModel: OutletViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                onAddClick()
            },
            modifier = Modifier.padding(end = 20.r(), bottom = 40.r()),
            containerColor = APP_DEFAULT_COLOR
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(30.r()),
                tint = Color.White
            )
        }
    }, floatingActionButtonPosition = FabPosition.End) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppToolbarCompose(
                onClick = { onBack() },
                icon = DesignSystemR.drawable.ic_back,
                title = CommonR.string.back
            )


            val lazyColumnListState = rememberLazyListState()
            LazyColumn(
                state = lazyColumnListState,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                items(50) { index ->
                    Spacer(modifier = Modifier.height(10.r()))
                    ItemCompose(viewModel.state.outletList[0], index)
                }
            }
        }
    }

}

@Composable
fun ItemCompose(response: OutletResponse, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.r()),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = if (index % 2 == 0) Color.Yellow else Color.Green)
        ) {
            Spacer(
                modifier = Modifier
                    .width(10.r())
                    .background(color = Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.r()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = response.outletName,
                            style = subHeading1TextStyle.copy(color = Color.Black)
                        )

                        Text(
                            text = "Owner: ${response.ownerName}",
                            style = bodyRegularTextStyle.copy(fontSize = 14.ssp(), fontWeight = FontWeight.W500, color = Color.DarkGray)
                        )
                        Text(
                            text = "Address: ${response.address}",
                            style = bodyRegularTextStyle.copy(fontSize = 14.ssp(), fontWeight = FontWeight.W500, color = Color.DarkGray)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Phone, contentDescription = "", tint = Color.Green)
                }
            }
        }
    }
}

@Composable
@Preview
fun previewItemCompose() {
    ItemCompose(response = OUTLET_LIST[0], index = 1)
}

@Composable
//@Preview
fun PreviewCustomerAddScreen() {
    OutletScreen(onBack = {}, onAddClick = {})
}
