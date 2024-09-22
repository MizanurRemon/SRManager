package com.srmanager.core.designsystem.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.srmanager.core.designsystem.r

@Composable
fun TableHeaderComponentWithoutColumnDividers(
    headerTableTitles: List<String>,
    headerTitlesTextStyle: TextStyle,
    headerTitlesBackGroundColor: Color,
    dividerThickness: Dp,
    contentAlignment: Alignment,
    textAlign: TextAlign,
    tablePadding: Dp,
    columnToIndexIncreaseWidth: Int?,
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(headerTitlesBackGroundColor)
                .padding(horizontal = tablePadding),
        ) {
            headerTableTitles.forEachIndexed { index, title ->
                val weight = if (index == columnToIndexIncreaseWidth) 8f else 2f
                Box(
                    modifier = Modifier
                        .weight(weight),
                    contentAlignment = contentAlignment,
                ) {
                    Text(
                        text = title,
                        style = headerTitlesTextStyle,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(38.r())
                            .wrapContentHeight(),
                        textAlign = textAlign,
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerThickness)
                .background(headerTitlesBackGroundColor),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TableHeaderComponentWithoutColumnDividersPreview() {
    val titles = listOf("Team", "Home", "Away", "Points")

    TableHeaderComponentWithoutColumnDividers(
        headerTableTitles = titles,
        headerTitlesTextStyle = MaterialTheme.typography.bodySmall,
        headerTitlesBackGroundColor = Color.White,
        dividerThickness = 1.r(),
        contentAlignment = Alignment.Center,
        textAlign = TextAlign.Center,
        tablePadding = 0.r(),
        columnToIndexIncreaseWidth = null,
    )
}