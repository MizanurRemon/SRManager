package com.srmanager.core.designsystem.components.table

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
fun TableRowComponent(
    data: List<String>,
    rowBorderColor: Color,
    rowTextStyle: TextStyle,
    rowBackGroundColor: Color,
    contentAlignment: Alignment,
    textAlign: TextAlign,
    tablePadding: Dp,
    columnToIndexIncreaseWidth: Int?,
    dividerThickness: Dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowBackGroundColor)
            .padding(tablePadding),
    ) {
        data.forEachIndexed { index, title ->
            val weight = if (index == columnToIndexIncreaseWidth) 8f else 2f
            Box(
                modifier = Modifier
                    .weight(weight)
                    .border(
                        width = dividerThickness,
                        color = rowBorderColor,
                    ),
                contentAlignment = contentAlignment,
            ) {
                Text(
                    text = title,
                    style = rowTextStyle,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .height(38.r())
                        .wrapContentHeight()
                        .padding(horizontal = 8.r()),
                    textAlign = textAlign,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TableRowComponentPreview() {

    val tableData = listOf(
        "Man Utd", "26", "7", "95"
    )

    TableRowComponent(
        data = tableData,
        rowBorderColor = Color.Gray,
        rowTextStyle = MaterialTheme.typography.bodySmall,
        rowBackGroundColor = Color.White,
        contentAlignment = Alignment.Center,
        textAlign = TextAlign.Start,
        tablePadding = 0.r(),
        columnToIndexIncreaseWidth = null,
        dividerThickness = 1.r(),
    )
}