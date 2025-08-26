package com.clip.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
private fun fixFontSize(
    fontSize: TextUnit,
    lineHeight: TextUnit,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    color: Color
): TextStyle {
    val fontScale = LocalDensity.current.fontScale
    val fixedFontSize = (fontSize.value / fontScale).sp
    val fixedLineHeight = (lineHeight.value / fontScale).sp

    return TextStyle(
        fontSize = fixedFontSize,
        lineHeight = fixedLineHeight,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = color
    )
}

object AppTextStyles {

    val HEAD_30_42_BOLD
        @Composable get() = fixFontSize(
            fontSize = 30.sp,
            lineHeight = 42.sp,
            fontFamily = FontFamily(Font(R.font.bold)),
            fontWeight = FontWeight(700),
            color = Color(0xFF171717)
        )

    val HEAD_28_40_BOLD
        @Composable get() = fixFontSize(
            fontSize = 28.sp,
            lineHeight = 40.sp,
            fontFamily = FontFamily(Font(R.font.bold)),
            fontWeight = FontWeight(700),
            color = Color(0xFF171717)
        )

    val HEAD_24_34_BOLD
        @Composable get() = fixFontSize(
            fontSize = 24.sp,
            lineHeight = 34.sp,
            fontFamily = FontFamily(Font(R.font.bold)),
            fontWeight = FontWeight(700),
            color = Color(0xFF171717)
        )

    val HEAD_22_30_BOLD
        @Composable get() = fixFontSize(
            fontSize = 22.sp,
            lineHeight = 30.sp,
            fontFamily = FontFamily(Font(R.font.bold)),
            fontWeight = FontWeight(700),
            color = Color(0xFF171717)
        )

    val TITLE_20_28_SEMI
        @Composable get() = fixFontSize(
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontFamily = FontFamily(Font(R.font.semi_bold)),
            fontWeight = FontWeight(600),
            color = Color(0xFF171717)
        )

    val SUBTITLE_18_22_SEMI
        @Composable get() = fixFontSize(
            fontSize = 18.sp,
            lineHeight = 26.sp,
            fontFamily = FontFamily(Font(R.font.semi_bold)),
            fontWeight = FontWeight(600),
            color = Color(0xFF171717)
        )

    val SUBTITLE_16_24_SEMI
        @Composable get() = fixFontSize(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.semi_bold)),
            fontWeight = FontWeight(600),
            color = Color(0xFF171717)
        )

    val BODY_14_20_EXTRA_BOLD
        @Composable get() = fixFontSize(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.extra_bold)),
            fontWeight = FontWeight(800),
            color = Color(0xFF171717)
        )

    val BODY_14_20_MEDIUM
        @Composable get() = fixFontSize(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(500),
            color = Color(0xFF171717)
        )

    val BODY_14_20_REGULAR
        @Composable get() = fixFontSize(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.regular)),
            fontWeight = FontWeight(400),
            color = Color(0xFF171717)
        )

    val CAPTION_12_18_SEMI
        @Composable get() = fixFontSize(
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontFamily = FontFamily(Font(R.font.semi_bold)),
            fontWeight = FontWeight(600),
            color = Color(0xFF171717)
        )

    val CAPTION_12_14_MEDIUM
        @Composable get() = fixFontSize(
            fontSize = 10.sp,
            lineHeight = 14.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(500),
            color = Color(0xFF171717)
        )

    val CAPTION_10_14_MEDIUM
        @Composable get() = fixFontSize(
            fontSize = 10.sp,
            lineHeight = 14.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(500),
            color = Color(0xFF171717)
        )

    val SUBTITLE_18_26_SEMI
        @Composable get() = fixFontSize(
            fontSize = 18.sp,
            lineHeight = 26.sp,
            fontFamily = FontFamily(Font(R.font.semi_bold)),
            fontWeight = FontWeight(600),
            color = Color(0xFF171717)
        )
}
