package com.oneThing.first_matrch.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ButtonCenterLarge
import com.example.core.ColorStyle
import com.example.core.TopAppBarNumber
import com.oneThing.first_matrch.JOB

@Composable
fun JobGridSelector(
    selectJobs: Set<JOB>,
    onJobToggle: (JOB) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        items(JOB.entries.toList()) { job ->
            val selected = selectJobs.contains(job)
            ButtonCenterLarge(
                onClick = {
                    onJobToggle(job)
                },
                checked = selected,
                text = job.displayName
            )
        }
    }
}

@Composable
fun TopAppBarWithProgress(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (currentPage >= 0) currentPage / totalPage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Column {
        TopAppBarNumber(
            title = title,
            currentPage = if (currentPage >= 0) currentPage else 0,
            totalPage = totalPage,
            onBackClick = onBackClick
        )
        if (currentPage >= 0) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = ColorStyle.PURPLE_400,
                trackColor = ColorStyle.GRAY_200
            )
        } else {
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}
