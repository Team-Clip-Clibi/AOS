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
                    val (first, second) = result.data.job
                    val job = if (first == "NONE") {
                        null
                    } else {
                        JOB.fromDisplayName(first) ?: JOB.fromCode(second)
                    }

                    _uiState.update { current ->
                        current.copy(
                            job = job?.let { setOf(it) } ?: emptySet(),
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
            val currentJobs = current.job.toMutableSet()
            val updatedJobs = when {
                currentJobs.contains(data) -> {
                    currentJobs.remove(data)
                    currentJobs
                }

                currentJobs.size < 2 -> {
                    currentJobs.add(data)
                    currentJobs
                }

                else -> {
                    return@update current.copy(error = UiError.Error("To many job select"))
                }
            }
            current.copy(
                job = updatedJobs,
                error = UiError.None
            )
        }
    }

    fun updateJob() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = job.invoke(
                UpdateJob.Param(
                    Pair(_uiState.value.job.elementAt(0).name, _uiState.value.job.elementAt(1).name)
                )
            )) {
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
    val job: Set<JOB> = emptySet(),
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
