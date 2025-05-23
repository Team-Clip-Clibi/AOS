package com.oneThing.first_matrch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.GetUserInfo
import com.sungil.domain.useCase.UpdateDiet
import com.sungil.domain.useCase.UpdateJob
import com.sungil.domain.useCase.UpdateLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstMatchViewModel @Inject constructor(
    private val userInfo: GetUserInfo,
    private val job: UpdateJob,
    private val diet: UpdateDiet,
    private val language: UpdateLanguage,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FirstMatchData())
    val uiState: StateFlow<FirstMatchData> = _uiState.asStateFlow()

    private var destination: String = ""

    init {
        userData()
    }

    private fun userData() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is GetUserInfo.Result.Success -> {
                    val job = if (result.data.job == "NONE") {
                        JOB.fromCode("NONE") ?: JOB.NONE
                    } else {
                        JOB.fromCode(result.data.job) ?: JOB.NONE
                    }
                    _uiState.update { current ->
                        current.copy(
                            job = job,
                            diet = result.data.diet,
                            language = result.data.language
                        )
                    }
                }
            }
        }
    }

    fun selectJob(data: JOB) {
        _uiState.update { current ->
            current.copy(
                job = data,
                error = UiError.None
            )
        }
    }

    fun updateJob() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = job.invoke(UpdateJob.Param(_uiState.value.job.name))) {
                is UpdateJob.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is UpdateJob.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun updateDiet() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = diet.invoke(UpdateDiet.Param(_uiState.value.diet))) {
                is UpdateDiet.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is UpdateDiet.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun updateLanguage() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = language.invoke(UpdateLanguage.Param(_uiState.value.language))) {
                is UpdateLanguage.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is UpdateLanguage.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun initSuccessError() {
        _uiState.update { current ->
            current.copy(error = UiError.None, success = UiSuccess.None)
        }
    }

    fun diet(data: String) {
        _uiState.update { current ->
            current.copy(diet = data)
        }
    }

    fun language(data: String) {
        _uiState.update { current ->
            current.copy(language = data)
        }
    }

    fun destination(data: String) {
        destination = data
    }

    fun getDestination(): String {
        return destination
    }
}

data class FirstMatchData(
    val job: JOB = JOB.NONE,
    val diet: String = DIET.NONE.displayName,
    val language: String = "",
    val error: UiError = UiError.None,
    val success: UiSuccess = UiSuccess.None,
)

sealed class UiError {
    data object None : UiError()
    data class Error(val message: String) : UiError()
}

sealed class UiSuccess {
    data object None : UiSuccess()
    data class Success(val message: String) : UiSuccess()
}
