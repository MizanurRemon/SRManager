package com.srmanager.core.designsystem

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.srmanager.core.designsystem.theme.ColorTextSecondary
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyXSBoldTextStyle
import java.time.LocalDate


enum class Month(val monthNumber: Int, val fullName: String) {
    JANUARY(1, "January"),
    FEBRUARY(2, "February"),
    MARCH(3, "March"),
    APRIL(4, "April"),
    MAY(5, "May"),
    JUNE(6, "June"),
    JULY(7, "July"),
    AUGUST(8, "August"),
    SEPTEMBER(9, "September"),
    OCTOBER(10, "October"),
    NOVEMBER(11, "November"),
    DECEMBER(12, "December");

    companion object {
        // Function to get the Month enum by month number
        fun fromNumber(number: Int): Month? {
            return values().find { it.monthNumber == number }
        }

        // Function to get the current month
        fun currentMonth(): Month {
            val currentMonthNumber = java.time.LocalDate.now().monthValue
            return fromNumber(currentMonthNumber)!!
        }

        // Function to get a list of months with full names
        fun getFullNames(): List<String> {
            return values().map { it.fullName }
        }
    }
}

fun main() {
    // Get the list of month full names
    val monthNames = Month.getFullNames()
    println(monthNames) // Prints: [January, February, ..., December]
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonthPicker(
    openDialog: MutableState<Boolean>,
    confirmButtonCLicked: (Pair<String, String>) -> Unit,
    cancelClicked: () -> Unit
) {

    val currentMonth = LocalDate.now().monthValue
    val currentYear = LocalDate.now().year

    var month by remember {
        mutableStateOf(Month.fromNumber(currentMonth)!!.fullName)
    }

    var year by remember {
        mutableIntStateOf(currentYear)
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.r())
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(32.r())
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.r())
                    ) {

                        Text("Choose Month and Year")

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.r()),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                modifier = Modifier
                                    .size(35.r())
                                    .rotate(90f)
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            year--
                                        }
                                    ),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 20.r()),
                                text = year.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Icon(
                                modifier = Modifier
                                    .size(35.r())
                                    .rotate(-90f)
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            year++
                                        }
                                    ),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null
                            )

                        }


                        Card(
                            modifier = Modifier
                                .padding(top = 30.r())
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                maxItemsInEachRow = 3,
                                verticalArrangement = Arrangement.Center,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Month.getFullNames().forEach {
                                    Box(
                                        modifier = Modifier
                                            .width(100.r())
                                            .height(60.r())
                                            .clickable(
                                                indication = null,
                                                interactionSource = interactionSource,
                                                onClick = {
                                                    month = it
                                                }
                                            )
                                            .background(
                                                color = Color.Transparent
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        val animatedSize by animateDpAsState(
                                            targetValue = if (month == it) 60.r() else 0.r(),
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )

                                        /* Box(
                                             modifier = Modifier
                                                 .size(animatedSize)
                                                 .background(
                                                     color = if (month == it) ColorTextSecondary else Color.Transparent,
                                                     shape = RoundedCornerShape(30.r())
                                                 )
                                         )*/

                                        Text(
                                            text = it,
                                            style = bodyXSBoldTextStyle.copy(color = if (month == it) Color.White else Color.Black),
                                            modifier = Modifier
                                                .background(
                                                    color = if (month == it) ColorTextSecondary else Color.Transparent,
                                                    shape = RoundedCornerShape(16.r())
                                                )
                                                .padding(vertical = 5.r(), horizontal = 10.r())
                                        )

                                    }
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(20.r()))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 20.r()),
                            horizontalArrangement = Arrangement.End
                        ) {

                            Text(
                                text = "Cancel",
                                style = bodyBoldTextStyle.copy(color = ColorTextSecondary),
                                modifier = Modifier.clickable {
                                    cancelClicked()
                                }
                            )

                            Spacer(modifier = Modifier.width(20.r()))

                            Text(
                                text = "OK",
                                style = bodyBoldTextStyle,
                                modifier = Modifier.clickable {
                                    confirmButtonCLicked(
                                        Pair(
                                            first = month,
                                            second = year.toString()
                                        )
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
        confirmButtonCLicked = {}
    )
}
