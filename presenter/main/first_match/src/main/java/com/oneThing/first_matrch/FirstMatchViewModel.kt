package com.oneThing.first_matrch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clip.domain.useCase.GetUserInfo
import com.clip.domain.useCase.SaveFirstMatchInput
import com.clip.domain.useCase.UpdateDiet
import com.clip.domain.useCase.UpdateJob
import com.clip.domain.useCase.UpdateLanguage
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
    private val saveMatchInput : SaveFirstMatchInput
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
                            diet = DIET.fromDisplayNameOrEtc(result.data.diet).displayName,
                            dietContent = if (DIET.fromDisplayNameOrEtc(result.data.diet) == DIET.ETC) result.data.diet else "",
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
                error = UiError.None,
                dataChange = true
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
            when (val result = diet.invoke(
                UpdateDiet.Param(
                    diet = _uiState.value.diet,
                    dietContent = _uiState.value.dietContent
                )
            )) {
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
                    finishFirstMatch()
                }
            }
        }
    }

   private fun finishFirstMatch(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = saveMatchInput.invoke()){
                is SaveFirstMatchInput.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.message))
                    }
                }
                is SaveFirstMatchInput.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun initSuccessError() {
        _uiState.update { current ->
            current.copy(error = UiError.None, success = UiSuccess.None, dataChange = false)
        }
    }

    fun diet(data: String) {
        _uiState.update { current ->
            current.copy(
                diet = data,
                dietContent = "",
                dataChange = true
            )
        }
    }

    fun dietContent(data: String) {
        _uiState.update { current ->
            current.copy(
                dietContent = data,
                dataChange = true
            )
        }
    }

    fun language(data: String) {
        _uiState.update { current ->
            current.copy(
                language = data,
                dataChange = true
            )
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
    val dietContent: String = "",
    val language: String = "",
    val dataChange: Boolean = false,
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
