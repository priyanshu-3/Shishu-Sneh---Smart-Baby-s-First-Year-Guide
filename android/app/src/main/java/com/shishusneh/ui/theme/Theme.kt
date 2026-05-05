package com.shishusneh.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = Coral,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = CoralLight,
    secondary = Sage,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = SageLight,
    background = BgPrimary,
    surface = androidx.compose.ui.graphics.Color.White,
    onBackground = Navy,
    onSurface = Navy,
    error = Red,
    outline = Slate
)

val AppTypography = Typography(
    headlineLarge = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Navy),
    headlineMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy),
    titleLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Navy),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Navy),
    bodyLarge = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, color = NavyLight),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Slate),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
    labelSmall = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Slate)
)

@Composable
fun ShishuSnehTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}
