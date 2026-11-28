package com.caresync.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caresync.app.ui.theme.*

@Preview
@Preview(showSystemUi = true,
    device = Devices.PIXEL_3)
@Composable
fun SplashScreen()
{
    CareSyncTheme()
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Row()
            {
                Image(
                    painter = painterResource(id = R.drawable.caresync_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(116.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = DeepNavy)) {
                            append("Care")
                        }
                        withStyle(style=SpanStyle(color = CareBlue)){
                            append("Sync")
                        }
                    },

                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

            }
        }
    }
}
