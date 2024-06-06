package com.alphaomardiallo.freewifiparis.android.feature.wifihotSpots.presentation.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alphaomardiallo.freewifiparis.android.R

@Composable
fun HotSpotsMarker(
    colors: IconColor,
    isOperational: Boolean,
) {
    Icon(
        painterResource(id = if (isOperational) R.drawable.baseline_wifi_24 else R.drawable.baseline_wifi_off_24),
        tint = colors.iconColor,
        contentDescription = "",
        modifier = Modifier
            .height(60.dp)
            .padding(1.dp)
            .drawBehind {
                drawCircle(color = colors.backgroundColor, style = Fill)
                drawCircle(color = colors.borderColor, style = Stroke(width = 3f))
            }
            .padding(4.dp)
    )
}

data class IconColor(
    val iconColor: Color = Color.White,
    val backgroundColor: Color = Color.Transparent,
    val borderColor: Color = Color.Transparent,
) {

    fun getIconColor(isOperational: Boolean) =
        if (isOperational) isOperational() else isNotOperational()

    private fun isOperational() = IconColor(
        iconColor = Color.Green,
        backgroundColor = Color.White,
        borderColor = Color.Green
    )

    private fun isNotOperational() = IconColor(
        iconColor = Color.Red,
        backgroundColor = Color.White,
        borderColor = Color.Red
    )
}
