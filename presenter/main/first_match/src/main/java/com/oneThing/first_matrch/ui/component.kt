package com.oneThing.first_matrch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ButtonCenterLarge
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