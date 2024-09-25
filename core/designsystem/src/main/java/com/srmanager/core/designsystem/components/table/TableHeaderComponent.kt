package com.srmanager.core.designsystem.components.table


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.srmanager.core.designsystem.r

@Composable
fun TableHeaderComponent(
    headerTableTitles: List<String>,
    headerTitlesBorderColor: Color,
    headerTitlesTextStyle: TextStyle,
    headerTitlesBackGroundColor: Color,
    contentAlignment: Alignment,
    textAlign: TextAlign,
    tablePadding: Dp,
    columnToIndexIncreaseWidth: Int?,
    dividerThickness: Dp,
) {
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
                    .weight(weight)
                    .border(
                        width = dividerThickness,
                        color = headerTitlesBorderColor,
                    ),
                contentAlignment = contentAlignment,
            ) {
                Text(
                    text = title,
                    style = headerTitlesTextStyle,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .height(38.r())
                        .wrapContentHeight().padding(start = 8.r()),
                    textAlign = textAlign,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TableHeaderComponentPreview() {
    val titles = listOf("Team", "Home", "Away", "Points")
    TableHeaderComponent(
        headerTableTitles = titles,
        headerTitlesBorderColor = Color.Black,
        headerTitlesTextStyle = MaterialTheme.typography.labelMedium,
        headerTitlesBackGroundColor = Color.White,
        contentAlignment = Alignment.Center,
        textAlign = TextAlign.Center,
        tablePadding = 0.r(),
        columnToIndexIncreaseWidth = null,
        dividerThickness = 1.r(),
    )
}