package com.srmanager.app.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.srmanager.app.navigations.openOverlaySettings
import com.srmanager.core.designsystem.OnLifecycleEvent
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.h
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.ColorPrimaryDark
import com.srmanager.core.designsystem.theme.bodyRegularSpanStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.parseBold
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.designsystem.w
import com.srmanager.core.ui.DevicePreviews
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun OverlayPermissionScreen(
    navController: NavController,
    onClose: () -> Unit
) {

    val context = LocalContext.current
    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
            }

            else -> {
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = AppBrush)
            .padding(
                start = 40.w(),
                end = 40.w(),
                top = 20.h(),
                bottom = 40.h()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ConstraintLayout {

            val (appToolbarCompose, crossIcon) = createRefs()

            AppToolbarCompose(
                title = "",
                modifier =
                Modifier
                    .background(Color.Transparent)
                    .constrainAs(appToolbarCompose) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                onClose()
            }

            Image(
                painter = painterResource(id = DesignSystemR.drawable.ic_blue_cross),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(crossIcon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        onClose()
                    },
            )
        }



        Spacer(modifier = Modifier.height(15.h()))

        val headingString = buildAnnotatedString {

            withStyle(style = SpanStyle(ColorPrimaryDark)) {
                append(stringResource(CommonR.string.display) + " ")
            }
            append(stringResource(id = CommonR.string.over_the_apps))
        }
        Text(
            text = headingString,
            style = subHeading1TextStyle
        )

        Spacer(modifier = Modifier.height(30.h()))
        Text(
            text =
            stringResource(id = CommonR.string.missing_permission_overlay_details),
            style = bodyRegularTextStyle,
            modifier = Modifier.padding(vertical = 15.h())
        )
        Text(buildAnnotatedString {
            withStyle(
                style = bodyRegularSpanStyle.copy(fontWeight = FontWeight.Bold)
            ) {
                append("1. ")
            }
            withStyle(
                style = bodyRegularSpanStyle
            ) {
                append(stringResource(id = CommonR.string.accessibility_permission_step_1).parseBold())
            }
            append("\n")
            append("\n")
            withStyle(
                style = bodyRegularSpanStyle.copy(fontWeight = FontWeight.Bold)
            ) {
                append("2. ")
            }
            withStyle(
                style = bodyRegularSpanStyle
            ) {
                append(stringResource(id = CommonR.string.accessibility_permission_step_2).parseBold())
            }
            append("\n")
            append("\n")
            withStyle(
                style = bodyRegularSpanStyle.copy(fontWeight = FontWeight.Bold)
            ) {
                append("3. ")
            }
            withStyle(
                style = bodyRegularSpanStyle
            ) {
                append(stringResource(id = CommonR.string.accessibility_permission_step_3).parseBold())
            }

        })

        Spacer(modifier = Modifier.weight(1f))
        AppActionButtonCompose(
            stringId = CommonR.string.go_to_settings
        ) {
            openOverlaySettings(context = context)
        }

    }
}


@DevicePreviews
@Composable
fun PreviewMasterPermissionOverlayScreen() {
    return OverlayPermissionScreen(rememberNavController(), onClose = {})
}