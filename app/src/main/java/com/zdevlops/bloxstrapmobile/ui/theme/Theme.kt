package com.zdevlops.bloxstrapmobile.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val RoomWarmTop = Color(0xFF8E7F71)
val RoomWarmMid = Color(0xFF6A5D52)
val RoomWarmLow = Color(0xFF3D332B)
val RoomWarmDeep = Color(0xFF251E19)

val Taupe100 = Color(0xFFF6F2EE)
val Taupe200 = Color(0xFFEAE3DC)
val Taupe950 = Color(0xFF161311)
val AmberGlow = Color(0xFFF5D0A9)
val AlertRedBorder = Color(0x6BFCA5A5)
val AlertRedFill = Color(0x57B92D26)

private val BloxstrapColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Taupe950,
    secondary = AmberGlow,
    background = RoomWarmDeep,
    surface = Color(0x28FFF8F0),
    onBackground = Taupe100,
    onSurface = Taupe100
)

fun Modifier.frostedGlassCard(cornerRadius: Int = 24): Modifier {
    val shape = RoundedCornerShape(cornerRadius.dp)
    return this
        .clip(shape)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0x29FFFAF4),
                    Color(0x10FFF8F0),
                    Color(0x47191411)
                )
            )
        )
        .border(1.dp, Color(0x38FFF8F0), shape)
}

fun Modifier.frostedGlassDarkCard(cornerRadius: Int = 18): Modifier {
    val shape = RoundedCornerShape(cornerRadius.dp)
    return this
        .clip(shape)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x8F221C18),
                    Color(0xB814100D)
                )
            )
        )
        .border(1.dp, Color(0x29FFF8F0), shape)
}

fun Modifier.frostedAlertCard(cornerRadius: Int = 24): Modifier {
    val shape = RoundedCornerShape(cornerRadius.dp)
    return this
        .clip(shape)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    AlertRedFill,
                    Color(0xD1221412)
                )
            )
        )
        .border(1.dp, AlertRedBorder, shape)
}

@Composable
fun BloxstrapMobileTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BloxstrapColorScheme,
        content = content
    )
}
