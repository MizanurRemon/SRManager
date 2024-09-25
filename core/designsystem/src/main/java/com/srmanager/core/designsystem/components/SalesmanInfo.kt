package com.srmanager.core.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.srmanager.core.common.R
import com.srmanager.core.designsystem.components.table.DrawTable
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle

@Composable
fun SalesmanInfo(salesManName: String, salesManCode: String) {

    val tableData = listOf(
        listOf(stringResource(R.string.salesman_name), salesManName),
        listOf(stringResource(R.string.salesman_code), salesManCode)
    )


    DrawTable(
        data = tableData,
        enableTableHeaderTitles = false,
        disableVerticalDividers = false,
        dividerThickness = .5.r(),
        headerTableTitles = emptyList(),
        headerTitlesBackGroundColor = Color(0XFFE9AB17),
        tableRowColors = listOf(
            Color.White,
            Color.White
        ),
        contentAlignment = Alignment.CenterStart,
        textAlign = TextAlign.Start,
        horizontalDividerColor = Color.LightGray,
        rowBorderColor = Color.Black,
        rowTextStyle = bodyRegularTextStyle.copy(
            color = Color.Black, fontSize = 15.ssp()
        ),
    )
}
