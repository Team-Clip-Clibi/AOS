package com.clip.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clip.domain.useCase.SendReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val report: SendReport,
) : ViewModel() {
    private val _reportData = MutableStateFlow(
        Report(
            content = "",
            reportData = REPORT.NONE
        )
    )
    val reportData: StateFlow<Report> = _reportData

    private val _networkResult = MutableStateFlow<Result>(Result.Loading)
    val networkResult: StateFlow<Result> = _networkResult

    private val _showDialog = MutableStateFlow(false)
    val showDialog : StateFlow<Boolean> = _showDialog

    fun sendReport() {
        viewModelScope.launch {
            when (val result = report.invoke(
                SendReport.Param(
                    _reportData.value.content,
                    _reportData.value.reportData.name
                )
            )) {
                is SendReport.Result.Fail -> {
                    _networkResult.value = Result.Error(result.errorMessage)
                }

                is SendReport.Result.Success -> {
                    _networkResult.value = Result.Success(result.message)
                }
            }
        }
    }

    fun setCategory(data: REPORT) {
        _reportData.update { it.copy(reportData = data) }
    }

    fun setContent(data: String) {
        _reportData.update { it.copy(content = data) }
    }

    fun showDialog(){
        _showDialog.value = true
    }

    sealed interface Result {
        data class Success(val message: String) : Result
        data class Error(val errorMessage: String) : Result
        object Loading : Result
    }
    data class Report(
        val content: String,
        val reportData: REPORT,
    )
}
