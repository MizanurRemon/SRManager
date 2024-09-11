package com.srmanager.core.designsystem

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyXSBoldTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import java.time.LocalDate
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR


enum class Month(val fullName: String) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    companion object {

        fun getFullNames(): List<String> {
            return values().map { it.fullName.uppercase() }
        }
    }
}

@Composable
fun MonthPicker(
    openDialog: MutableState<Boolean>,
    confirmButtonCLicked: (Pair<String, String>) -> Unit,
    cancelClicked: () -> Unit,
    selectedMonth: Pair<String, String>
) {

    var month by remember {
        mutableStateOf(selectedMonth.first)
    }

    var year by remember {
        mutableIntStateOf(selectedMonth.second.toInt())
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (openDialog.value) {

        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                cancelClicked()
            },
            content = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.r())
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(32.r())
                        ), colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.r()),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(CommonR.string.choose_month_and_year),
                                style = bodyBoldTextStyle.copy(
                                    color = Color.Black
                                )
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Image(
                                painter = painterResource(DesignSystemR.drawable.ic_calendar),
                                contentDescription = null,
                                modifier = Modifier.size(20.r()),
                                colorFilter = ColorFilter.tint(
                                    color = APP_DEFAULT_COLOR
                                )
                            )
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.r()),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                modifier = Modifier
                                    .size(35.r())
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            year--
                                        }
                                    ),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.width(40.r()))

                            Text(
                                text = year.toString(),
                                style = subHeading1TextStyle.copy(
                                    color = Color.Black,
                                )
                            )

                            Spacer(modifier = Modifier.width(40.r()))

                            Icon(
                                modifier = Modifier
                                    .size(35.r())
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            if (year < LocalDate.now().year) {
                                                year++
                                            }

                                        }
                                    ),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = null
                            )

                        }


                        Card(
                            modifier = Modifier
                                .padding(10.r())
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ), elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.r()
                            )
                        ) {

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier.padding(5.r())
                            ) {
                                items(Month.getFullNames()) {
                                    Box(
                                        modifier = Modifier
                                            .width(100.r())
                                            .clickable(
                                                indication = null,
                                                interactionSource = interactionSource,
                                                onClick = {
                                                    month = it
                                                }
                                            )
                                            .background(
                                                color = Color.Transparent
                                            )
                                            .padding(vertical = 20.r()),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        val animatedSize by animateDpAsState(
                                            targetValue = if (it.equals(
                                                    month,
                                                    ignoreCase = true
                                                )
                                            ) 110.r() else 0.r(),
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )

                                        Box(
                                            modifier = Modifier
                                                .width(animatedSize)
                                                .height(40.r())
                                                .background(
                                                    color = if (it.equals(
                                                            month,
                                                            ignoreCase = true
                                                        )
                                                    ) APP_DEFAULT_COLOR else Color.Transparent,
                                                    shape = RoundedCornerShape(16.r())
                                                )
                                        )

                                        Text(
                                            text = it,
                                            style = bodyXSBoldTextStyle.copy(
                                                fontSize = 12.ssp(),
                                                color = if (it.equals(
                                                        month,
                                                        ignoreCase = true
                                                    )
                                                ) Color.White else Color.Black
                                            ),
                                            modifier = Modifier
                                                .padding(vertical = 5.r(), horizontal = 10.r())
                                        )

                                    }
                                }
                            }

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.r()),
                            horizontalArrangement = Arrangement.End
                        ) {

                            Text(
                                text = stringResource(CommonR.string.cancel),
                                style = bodyBoldTextStyle.copy(color = Color(0xFFE38E8E)),
                                modifier = Modifier.clickable {
                                    cancelClicked()
                                }
                            )

                            Spacer(modifier = Modifier.width(40.r()))

                            Text(
                                text = stringResource(CommonR.string.choose),
                                style = bodyBoldTextStyle.copy(
                                    color = Color(0xFF5FA8E2)
                                ),
                                modifier = Modifier.clickable {
                                    confirmButtonCLicked(
                                        Pair(month, year.toString())
                                    )
                                }
                            )

                        }

                    }

                }

            }
        )

    }

}


@Composable
@Preview
fun PreviewMonthPickerDialog() {
    val openDialog = remember {
        mutableStateOf(true)
    }
    MonthPicker(
        openDialog = openDialog,
        cancelClicked = {

        },
        confirmButtonCLicked = {},
        selectedMonth = Pair(LocalDate.now().month.name, LocalDate.now().year.toString())
    )
}
