package com.srmanager.core.designsystem.components.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle

@Composable
fun DrawTable(
    data: List<List<String>>,
    enableTableHeaderTitles: Boolean = true,
    headerTableTitles: List<String>,
    headerTitlesBorderColor: Color = Color.Black,
    headerTitlesTextStyle: TextStyle = bodyBoldTextStyle,
    headerTitlesBackGroundColor: Color = Color.White,
    tableRowColors: List<Color> = listOf(
        Color.White,
        Color.White,
    ),
    rowBorderColor: Color = Color.Black,
    rowTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    disableVerticalDividers: Boolean = false,
    dividerThickness: Dp = 1.r(),
    horizontalDividerColor: Color = Color.Black,
    contentAlignment: Alignment = Alignment.Center,
    textAlign: TextAlign = TextAlign.Center,
    tablePadding: Dp = 0.r(),
    columnToIndexIncreaseWidth: Int? = null,
) {

    Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        if (enableTableHeaderTitles) {
            if (disableVerticalDividers) {
                TableHeaderComponentWithoutColumnDividers(
                    headerTableTitles = headerTableTitles,
                    headerTitlesTextStyle = headerTitlesTextStyle,
                    headerTitlesBackGroundColor = headerTitlesBackGroundColor,
                    dividerThickness = dividerThickness,
                    contentAlignment = contentAlignment,
                    textAlign = textAlign,
                    tablePadding = tablePadding,
                    columnToIndexIncreaseWidth = columnToIndexIncreaseWidth,
                )
            } else {
                TableHeaderComponent(
                    headerTableTitles = headerTableTitles,
                    headerTitlesBorderColor = headerTitlesBorderColor,
                    headerTitlesTextStyle = headerTitlesTextStyle,
                    headerTitlesBackGroundColor = headerTitlesBackGroundColor,
                    contentAlignment = contentAlignment,
                    textAlign = textAlign,
                    tablePadding = tablePadding,
                    dividerThickness = dividerThickness,
                    columnToIndexIncreaseWidth = columnToIndexIncreaseWidth,
                )
            }
        }

        data.forEachIndexed { index, data ->
            val tableRowBackgroundColor = if (index % 2 == 0) {
                tableRowColors[0]
            } else {
                tableRowColors[1]
            }

            if (disableVerticalDividers) {
                TableRowComponentWithoutDividers(
                    data = data,
                    rowTextStyle = rowTextStyle,
                    rowBackGroundColor = tableRowBackgroundColor,
                    dividerThickness = dividerThickness,
                    horizontalDividerColor = horizontalDividerColor,
                    contentAlignment = contentAlignment,
                    textAlign = textAlign,
                    tablePadding = tablePadding,
                    columnToIndexIncreaseWidth = columnToIndexIncreaseWidth,
                )
            } else {
                TableRowComponent(
                    data = data,
                    rowBorderColor = rowBorderColor,
                    dividerThickness = dividerThickness,
                    rowTextStyle = rowTextStyle,
                    rowBackGroundColor = tableRowBackgroundColor,
                    contentAlignment = contentAlignment,
                    textAlign = textAlign,
                    tablePadding = tablePadding,
                    columnToIndexIncreaseWidth = columnToIndexIncreaseWidth,
                )
            }
        }
    }

}