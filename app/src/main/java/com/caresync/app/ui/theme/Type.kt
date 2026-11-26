package com.caresync.app.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.caresync.app.R


val interFamily = FontFamily(
    Font(
        resId = R.font.inter
    )

)


val testFamily = FontFamily(
    Font(
        resId = R.font.test_font
    )

)

private val defaultTypography = Typography()

// Set of Material typography styles to start with

/***
 * Only some Types have been overridden.
 * headlineLarge: H1
 * headlineMedium: H2
 * headlineSmall: H3
 * bodyMedium: Body
 * labelSmall: Caption
 */
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = interFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = interFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = interFamily),
    headlineLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = interFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = interFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = interFamily),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = interFamily),
    bodyMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = interFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = interFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = interFamily),
    labelSmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )
)


//Use this to tell when not using the customized Typography
/**
 * Use this in order to easily see text that does not use the customized typography
 */
@Suppress("unused")
val TestTypography = Typography(
    displayLarge = Typography.displayLarge.copy(fontFamily = testFamily),
    displayMedium = Typography.displayMedium.copy(fontFamily = testFamily),
    displaySmall = Typography.displaySmall.copy(fontFamily = testFamily),
//    headlineLarge = Typography.headlineLarge.copy(fontFamily = testFamily),
//    headlineMedium = Typography.headlineMedium.copy(fontFamily = testFamily),
//    headlineSmall = Typography.headlineSmall.copy(fontFamily = testFamily),
    titleLarge = Typography.titleLarge.copy(fontFamily = testFamily),
    titleMedium = Typography.titleMedium.copy(fontFamily = testFamily),
    titleSmall = Typography.titleSmall.copy(fontFamily = testFamily),
    bodyLarge = Typography.bodyLarge.copy(fontFamily = testFamily),
//    bodyMedium = Typography.bodyMedium.copy(fontFamily = testFamily),
    bodySmall = Typography.bodySmall.copy(fontFamily = testFamily),
    labelLarge = Typography.labelLarge.copy(fontFamily = testFamily),
    labelMedium = Typography.labelMedium.copy(fontFamily = testFamily),
//    labelSmall = Typography.labelSmall.copy(fontFamily = testFamily)
)


