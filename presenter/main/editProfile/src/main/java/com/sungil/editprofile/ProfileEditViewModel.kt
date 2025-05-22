package com.sungil.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.ActivateHaptic
import com.sungil.domain.useCase.GetUserInfo
import com.sungil.domain.useCase.LogOut
import com.sungil.domain.useCase.SaveChangeProfileData
import com.sungil.domain.useCase.SignOut
import com.sungil.domain.useCase.UpdateDiet
import com.sungil.domain.useCase.UpdateJob
import com.sungil.domain.useCase.UpdateLanguage
import com.sungil.domain.useCase.UpdateLove
import com.sungil.domain.useCase.UpdateNickName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfo,
    private val changeNickName: UpdateNickName,
    private val haptic: ActivateHaptic,
    private val changeJob: UpdateJob,
    private val updateLoveState: UpdateLove,
    private val updateLanguage: UpdateLanguage,
    private val logout: LogOut,
    private val signOut: SignOut,
    private val diet: UpdateDiet,
) : ViewModel() {
    private var _uiState = MutableStateFlow(EditProfileData())
    val uiState: StateFlow<EditProfileData> = _uiState.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            when (val result = userInfoUseCase.invoke()) {
                is GetUserInfo.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is GetUserInfo.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            name = result.data.userName,
                            nickName = result.data.nickName ?: "Error",
                            phoneNumber = result.data.phoneNumber,
                            job = result.data.job,
                            loveState = result.data.loveState.first,
                            meetSame = result.data.loveState.second,
                            diet = result.data.diet,
                            language = result.data.language
                        )
                    }
                }
            }
        }
    }

    fun changeNickName() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                changeNickName.invoke(UpdateNickName.Param(_uiState.value.nickName))) {
                is UpdateNickName.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.message))
                    }
                }

                is UpdateNickName.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            success = UiSuccess.Success(result.message)
                        )
                    }
                }
            }
        }
    }

    fun changeJob() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = changeJob.invoke(UpdateJob.Param(_uiState.value.job))) {
                is UpdateJob.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is UpdateJob.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            success = UiSuccess.Success(result.message)
                        )
                    }
                }
            }
        }
    }

    fun changeLanguage() {
        viewModelScope.launch {
            when (val result =
                updateLanguage.invoke(UpdateLanguage.Param(_uiState.value.language))) {
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

    fun sendLoveState() {
        viewModelScope.launch {
            when (val result = updateLoveState.invoke(
                UpdateLove.Param(
                    _uiState.value.loveState,
                    _uiState.value.meetSame.toString()
                )
            )) {
                is UpdateLove.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is UpdateLove.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            when (val result = logout.invoke()) {
                is LogOut.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is LogOut.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = signOut.invoke()) {
                is SignOut.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is SignOut.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(success = UiSuccess.Success(result.message))
                    }
                }
            }
        }
    }

    fun updateDiet() {
        viewModelScope.launch {
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

    fun isDialogShow(data: Boolean) {
        _uiState.update { current ->
            current.copy(isDialogShow = data)
        }
    }

    fun initSuccessError() {
        _uiState.update { current ->
            current.copy(
                success = UiSuccess.None,
                error = UiError.None,
                buttonRun = false
            )
        }
    }

    fun setNickName(data: String) {
        _uiState.update { current ->
            current.copy(
                nickName = data,
                buttonRun = true
            )
        }
    }

    fun setLanguage(data: LANGUAGE) {
        _uiState.update { current ->
            current.copy(
                language = data.name,
                buttonRun = true
            )
        }
    }

    fun setJob(data : JOB){
        _uiState.update { current ->
            current.copy(
                job = data.name,
                buttonRun = true
            )
        }
    }

    fun changeLoveState(data : LOVE){
        _uiState.update { current ->
            current.copy(
                loveState = data.name,
                buttonRun = true
            )
        }
    }

    fun changeMeetState(data : MEETING){
        _uiState.update { current ->
            current.copy(
                meetSame = data.displayName,
                buttonRun = true
            )
        }
    }

    fun changeSignOut(data : SignOutData){
        _uiState.update { current ->
            current.copy(
                signOut = data.name
            )
        }
    }

    fun changeSignOutContent(data : String){
        _uiState.update { current ->
            current.copy(
                signOutContent = data
            )
        }
    }

}


data class EditProfileData(
    val name: String = "",
    val nickName: String = "",
    val phoneNumber: String = "",
    val job: String = "",
    val loveState: String = "",
    val meetSame: Boolean = false,
    val diet: String = "",
    val language: String = "",
    val signOut : String ="",
    val signOutContent : String = "",
    val isDialogShow: Boolean = false,
    val buttonRun: Boolean = false,
    val success: UiSuccess = UiSuccess.None,
    val error: UiError = UiError.None,
)

sealed class UiSuccess {
    data object None : UiSuccess()
    data class Success(val message: String) : UiSuccess()
}

sealed class UiError {
    data object None : UiError()
    data class Error(val message: String) : UiError()
}
