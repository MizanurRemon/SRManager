package com.srmanager.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle

@Composable
fun ActionButton(onItemClick: () -> Unit, title: Int) {
    Card(
        shape = RoundedCornerShape(15.r()),
        modifier = Modifier
            .clickable {
                onItemClick()
            }
            .padding(5.r())
            .shadow(
                elevation = 4.r(),
                spotColor = APP_DEFAULT_COLOR,
                shape = RoundedCornerShape(15.r())
            ),
        colors = CardDefaults.cardColors(containerColor = APP_DEFAULT_COLOR)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(title),
                style = bodyBoldTextStyle.copy(color = Color.White),
                modifier = Modifier.padding(vertical = 30.r())
            )
        }
    }
}