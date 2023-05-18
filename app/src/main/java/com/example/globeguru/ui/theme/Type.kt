package com.example.globeguru.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.globeguru.R

val CenturyGothic = FontFamily(
    Font(R.font.century_gothic_bold),
)

val QuickSand = FontFamily(
    Font(R.font.quicksand_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = QuickSand,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(
        fontFamily = QuickSand,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp),

    titleLarge = TextStyle(
        fontFamily = CenturyGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = CenturyGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)

