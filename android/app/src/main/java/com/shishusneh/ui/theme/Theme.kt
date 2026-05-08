package com.shishusneh.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shishusneh.R

val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans)
)

private val LightColorScheme = lightColorScheme(
    primary = Coral,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = CoralLight,
    onPrimaryContainer = CoralDark,
    secondary = Sage,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = SageLight,
    onSecondaryContainer = SageDark,
    tertiary = Terracotta,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    tertiaryContainer = TerracottaLight,
    onTertiaryContainer = TerracottaDark,
    background = Cream,
    surface = Cream,
    surfaceVariant = SurfaceContainer,
    onBackground = Navy,
    onSurface = Navy,
    onSurfaceVariant = NavyLight,
    error = Red,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = ErrorContainer,
    outline = Slate
)

val AppTypography = Typography(
    headlineLarge = TextStyle(fontFamily = PlusJakartaSans, fontSize = 32.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp, color = Navy),
    headlineMedium = TextStyle(fontFamily = PlusJakartaSans, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 32.sp, color = Navy),
    headlineSmall = TextStyle(fontFamily = PlusJakartaSans, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp, color = Navy),
    titleLarge = TextStyle(fontFamily = PlusJakartaSans, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Navy),
    titleMedium = TextStyle(fontFamily = PlusJakartaSans, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Navy),
    bodyLarge = TextStyle(fontFamily = PlusJakartaSans, fontSize = 18.sp, fontWeight = FontWeight.Normal, lineHeight = 28.sp, color = NavyLight),
    bodyMedium = TextStyle(fontFamily = PlusJakartaSans, fontSize = 16.sp, fontWeight = FontWeight.Normal, lineHeight = 24.sp, color = NavyLight),
    labelLarge = TextStyle(fontFamily = PlusJakartaSans, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = PlusJakartaSans, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 18.sp, color = Slate)
)

@Composable
fun ShishuSnehTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}

