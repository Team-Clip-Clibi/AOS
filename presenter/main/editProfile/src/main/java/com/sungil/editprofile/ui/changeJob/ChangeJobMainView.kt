package com.sungil.editprofile.ui.changeJob

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ERROR_NONE_DATA_SELECT
import com.sungil.editprofile.JOB
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.JobGridSelector

@Composable
internal fun ChangeJobMainView(
    paddingValues: PaddingValues,
    viewModel: ProfileEditViewModel,
    snackBarHost: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.error , uiState.success) {
        when(val error = uiState.error){
            is UiError.Error -> {
                when(error.message){
                    ERROR_NETWORK -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.txt_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                    ERROR_FAIL_SAVE -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_save_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                    ERROR_NONE_DATA_SELECT ->{
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_select_data),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
            UiError.None -> Unit
        }
        when(uiState.success){
            UiSuccess.None -> Unit
            is UiSuccess.Success -> {
                snackBarHost.showSnackbar(
                    message = context.getString(R.string.msg_change_job_success),
                    duration = SnackbarDuration.Short
                )
                viewModel.initSuccessError()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .navigationBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .padding(start = 17.dp, end = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.txt_job_select_one),
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            JobGridSelector(
                selectedJobs = JOB.fromDisplayName(uiState.job),
                onJobToggle = viewModel::setJob,
                modifier = Modifier.fillMaxWidth()
            )
        }

        HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
        Spacer(modifier = Modifier.height(8.dp))
        ButtonXXLPurple400(
            onClick = { viewModel.changeJob() },
            buttonText = stringResource(R.string.btn_finish),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            isEnable = uiState.buttonRun
        )
    }
}