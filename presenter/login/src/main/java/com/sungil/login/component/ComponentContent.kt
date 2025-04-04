package com.sungil.login.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.core.AppTextStyles
import com.sungil.login.R
import kotlin.math.absoluteValue

@Composable
fun CustomText(
    text: String,
    style : TextStyle,
    textColor: Color,
    maxLine : Int
) {
    Text(
        text = text,
        style = style,
        color = textColor,
        maxLines = maxLine
    )
}

@Composable
fun CustomButton(
    modifier: Modifier,
    shape : RoundedCornerShape,
    buttonColor : ButtonColors,
    textColor : Color,
    text : String,
    onclick :() -> Unit,
    imageId : Int = -1,
    imageDescription : String = "noData"
){
    Button(
        modifier = modifier,
        shape = shape,
        colors = buttonColor,
        onClick = onclick
    ) {
        if(imageId != -1){
            Image(
                painter = painterResource(id = imageId),
                contentDescription = imageDescription,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Text(
            text = text,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = textColor,
        )
    }
}

@Composable
fun CustomPager(
    modifier: Modifier,
    text: List<String>,
    textSize: TextUnit,
    font: FontFamily,
    pageCount: Int,

    ) {
    val testImage = listOf(
        R.drawable.ic_cat,
        R.drawable.ic_dog
    )
    val testCaption = listOf(
        "나용","멍멍"
    )
    val pageState = rememberPagerState(pageCount = {
        pageCount
    })
    HorizontalPager(state = pageState) { page ->
        Card(
            modifier = modifier
                .graphicsLayer {
                    val pageOffset = (
                            (pageState.currentPage - page) + pageState.currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
            shape =  RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = testImage[page]),
                contentDescription = "page$page Image",
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxSize()
            )
            Text(
                text = text[page],
                fontSize = textSize,
                fontFamily = font,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

}
@Composable
fun PageIndicator(
    numberOfPages: Int,
    selectedPage: Int = 0,
    selectedColor: Color = Color.White,
    defaultColor: Color = Color.Gray,
    defaultRadius: Dp = 8.dp,
    selectedLength: Dp = 25.dp,
    space: Dp = 8.dp, // 간격을 8dp로 설정
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center, // 가운데 정렬
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space) // 간격을 설정
        ) {
            repeat(numberOfPages) {
                Indicator(
                    isSelected = it == selectedPage,
                    selectedColor = selectedColor,
                    defaultColor = defaultColor,
                    defaultRadius = defaultRadius,
                    selectedLength = selectedLength,
                )
            }
        }
    }
}

/**
 * pager indicator item
 */
@Composable
fun Indicator(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    modifier: Modifier = Modifier.height(defaultRadius)
) {
    val width by animateDpAsState(
        targetValue = if (isSelected) selectedLength else defaultRadius,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Box(
        modifier = modifier
            .width(width)
            .clip(CircleShape)
            .background(color = if (isSelected) selectedColor else defaultColor)
    )
}