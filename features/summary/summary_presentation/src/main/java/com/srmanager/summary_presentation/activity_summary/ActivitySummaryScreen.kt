package com.srmanager.summary_presentation.activity_summary

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.common.util.SUMMARY_FILTERED_ITEMS
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.ColorTextPrimary
import com.srmanager.core.designsystem.theme.TOP_INDEXER_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun ActivitySummaryScreen(
    onBack: () -> Unit,
    state: ActivitySummaryState,
    uiEvent: Flow<UiEvent>,
    onEvent: (ActivitySummaryEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .clickable {
                onEvent(ActivitySummaryEvent.OnFilterItemSelection(false))
            },
    ) {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.back
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.r())
                .padding(top = 10.r()),
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 5.r(),
                        spotColor = Color.Gray,
                        shape = RoundedCornerShape(15.r())
                    )
                    .clickable {
                        onEvent(ActivitySummaryEvent.OnFilterItemSelection(true))
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.r(), vertical = 15.r()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(state.selectedItem), style = bodyRegularTextStyle)

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(24.r())
                    )
                }
            }


            Box(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(10.r()))

                    when (state.selectedItem) {
                        SUMMARY_FILTERED_ITEMS[0] -> {

                            VisitingItem(
                                bgColor = TOP_INDEXER_COLOR,
                                title = CommonR.string.total_visited_outlet,
                                value = "15"
                            )

                            VisitingItem(
                                bgColor = Color.White,
                                title = CommonR.string.total_ordered_outler,
                                value = "15"
                            )

                            VisitingItem(
                                bgColor = TOP_INDEXER_COLOR,
                                title = CommonR.string.total_ordered_amount,
                                value = "15"
                            )
                        }

                        SUMMARY_FILTERED_ITEMS[1] -> {
                            VisitingItem(
                                bgColor = TOP_INDEXER_COLOR,
                                title = CommonR.string.total_visited_outlet,
                                value = "15"
                            )

                            VisitingItem(
                                bgColor = Color.White,
                                title = CommonR.string.tota_not_visited_outlet,
                                value = "15"
                            )

                            VisitingItem(
                                bgColor = TOP_INDEXER_COLOR,
                                title = CommonR.string.total_ordered_outler,
                                value = "15"
                            )

                            VisitingItem(
                                bgColor = Color.White,
                                title = CommonR.string.total_not_ordered_outlet,
                                value = "15"
                            )

                            VisitingItem(
                                bgColor = TOP_INDEXER_COLOR,
                                title = CommonR.string.total_ordered_amount,
                                value = "15"
                            )
                        }
                    }
                }

                if (state.isDropDownOpened) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.r())
                            .shadow(
                                elevation = 2.r(),
                                shape = RoundedCornerShape(15.r())
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                    ) {
                        SUMMARY_FILTERED_ITEMS.forEachIndexed { index, item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(ActivitySummaryEvent.OnItemSelection(item))
                                    }
                            ) {
                                Text(
                                    text = stringResource(item),
                                    style = bodyRegularTextStyle.copy(
                                        color = ColorTextPrimary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier
                                        .padding(15.r())
                                )

                                if (index != SUMMARY_FILTERED_ITEMS.size - 1) {
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.r())
                                            .background(color = Color.LightGray)
                                    )
                                }
                            }

                        }

                    }
                }
            }

        }
    }
}

@Composable
fun VisitingItem(bgColor: Color, @StringRes title: Int, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.r(), horizontal = 10.r())
        ) {
            Text(text = stringResource(title), style = bodyRegularTextStyle)

            Spacer(modifier = Modifier.weight(1f))

            Text(text = value, style = bodyBoldTextStyle)
        }
    }
}


@Composable
@Preview
fun PreviewActivitySummaryScreen() {
    ActivitySummaryScreen(
        onBack = {},
        state = ActivitySummaryState(),
        uiEvent = flow { },
        onEvent = {}
    )
}