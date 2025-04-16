package com.example.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object AppTextStyles {

    val HEAD_30_20_BOLD = TextStyle(
        fontSize = 30.sp,
        lineHeight = 42.sp,
        fontFamily = FontFamily(Font(R.font.bold)),
        fontWeight = FontWeight(700),
        color = Color(0xFF171717)
    )

    val HEAD_28_40_BOLD = TextStyle(
        fontSize = 28.sp,
        lineHeight = 40.sp,
        fontFamily = FontFamily(Font(R.font.bold)),
        fontWeight = FontWeight(700),
        color = Color(0xFF171717),
    )

    val HEAD_24_34_BOLD = TextStyle(
        fontSize = 24.sp,
        lineHeight = 34.sp,
        fontFamily = FontFamily(Font(R.font.bold)),
        fontWeight = FontWeight(700),
        color = Color(0xFF171717),
    )

    val HEAD_22_30_BOLD = TextStyle(
        fontSize = 22.sp,
        lineHeight = 30.sp,
        fontFamily = FontFamily(Font(R.font.bold)),
        fontWeight = FontWeight(700),
        color = Color(0xFF171717)
    )

    val TITLE_20_28_SEMI = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontFamily = FontFamily(Font(R.font.semi_bold)),
        fontWeight = FontWeight(600),
        color = Color(0xFF171717),
    )

    val SUBTITLE_18_22_SEMI = TextStyle(
        fontSize = 18.sp,
        lineHeight = 26.sp,
        fontFamily = FontFamily(Font(R.font.semi_bold)),
        fontWeight = FontWeight(600),
        color = Color(0xFF171717),
    )

    val SUBTITLE_16_24_SEMI = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = FontFamily(Font(R.font.semi_bold)),
        fontWeight = FontWeight(600),
        color = Color(0xFF171717),
    )

    val BODY_14_20_MEDIUM = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.medium)),
        fontWeight = FontWeight(500),
        color = Color(0xFF171717),
    )

    val BODY_14_20_REGULAR = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.regular)),
        fontWeight = FontWeight(400),
        color = Color(0xFF171717),
    )

    val CAPTION_12_18_SEMI = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.semi_bold)),
        fontWeight = FontWeight(600),
        color = Color(0xFF171717),
    )

    val CAPTION_12_14_MEDIUM = TextStyle(
        fontSize = 10.sp,
        lineHeight = 14.sp,
        fontFamily = FontFamily(Font(R.font.medium)),
        fontWeight = FontWeight(500),
        color = Color(0xFF171717),
    )

    val SUBTITLE_18_26_SEMI = TextStyle(
        fontSize = 18.sp,
        lineHeight = 26.sp,
        fontFamily = FontFamily(Font(R.font.semi_bold)),
        fontWeight = FontWeight(600),
        color = Color(0xFF171717)
    )

}