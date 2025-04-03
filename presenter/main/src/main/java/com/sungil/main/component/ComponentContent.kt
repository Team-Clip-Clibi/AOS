package com.sungil.main.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.AppTextStyles
import com.sungil.main.Screen
import com.sungil.main.bottomNavItems

@Composable
fun BottomNavigation(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .border(1.dp, Color(0xFFF7F7F7))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = currentRoute == item.screenRoute
            BottomNavItem(
                item = item,
                isSelected = isSelected,
                onClick = {
                    if (currentRoute != item.screenRoute) {
                        navController.navigate(item.screenRoute) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
            if (index != bottomNavItems.lastIndex) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@Composable
fun BottomNavItem(
    item: Screen,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFF9F0FF) else Color.Transparent,
        animationSpec = tween(250)
    )

    val contentColor = if (isSelected) Color(0xFF6700CE) else Color(0xFF989898)

    Box(
        modifier = modifier
            .fillMaxHeight()
            .noVisualFeedbackClickable(onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(backgroundColor)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.title),
                modifier = Modifier.size(20.dp),
                tint = contentColor
            )

            Crossfade(targetState = isSelected, animationSpec = tween(250), label = "") { selected ->
                if (selected) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = item.title),
                        color = contentColor,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI
                    )
                }
            }
        }
    }
}


fun Modifier.noVisualFeedbackClickable(onClick: () -> Unit): Modifier = composed {
    this.then(
        Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { onClick() })
        }
    )
}
