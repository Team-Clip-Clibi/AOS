package com.sungil.editprofile.ui.changeJob

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomTitle1624Semi
import com.sungil.editprofile.ui.JobGridSelector

@Composable
internal fun ChangeJobMainView(
    paddingValues: PaddingValues,
    viewModel: ProfileEditViewModel,
    snackBarHost: SnackbarHostState,
) {
    val selectedJobs by viewModel.selectedJobs.collectAsState()
    val isOverLimit by viewModel.jobSelectionError.collectAsState()
    val isChanged = remember(selectedJobs) { viewModel.isJobSelectionChanged() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .navigationBarsPadding()
            .padding(start = 17.dp, end = 16.dp)
    ) {

        CustomTitle1624Semi(
            text = stringResource(R.string.txt_job_select_two),
            color = if (isOverLimit) 0xFFFF4F4F else 0xFFFB4F4F
        )

        Spacer(modifier = Modifier.height(24.dp))

        JobGridSelector(
            selectedJobs = selectedJobs,
            onJobToggle = viewModel::toggleJob,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
        Spacer(modifier = Modifier.height(8.dp))

        CustomButton(
            stringResource(R.string.btn_finish),
            onclick = { viewModel.changeJob() },
            enable = isChanged && selectedJobs.size <= 2
        )
    }
}
