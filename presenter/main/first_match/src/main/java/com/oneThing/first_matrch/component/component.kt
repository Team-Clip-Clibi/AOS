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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.ButtonL
import com.example.core.ColorStyle
import com.example.core.TopAppBarNumber
import com.oneThing.first_matrch.JOB

@Composable
fun JobGridSelector(
    selectJobs: JOB,
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
            if (job != JOB.NONE) {
                ButtonL(
                    onClick = {
                        onJobToggle(job)
                    },
                    isSelected = job == selectJobs,
                    text = job.displayName,
                    buttonColor = if(job == selectJobs) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
                    disEnableButtonColor = ColorStyle.GRAY_100,
                    borderUse = job == selectJobs,
                    borderColor = if(job == selectJobs) ColorStyle.PURPLE_200 else Color.Transparent
                )
            }
        }
    }
}

