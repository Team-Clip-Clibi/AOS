package com.sungil.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.BannerData
import com.sungil.domain.useCase.CheckAlreadySignUp
import com.sungil.domain.useCase.CheckAppVersion
import com.sungil.domain.useCase.CheckPermissionShow
import com.sungil.domain.useCase.GetBanner
import com.sungil.domain.useCase.GetFcmToken
import com.sungil.domain.useCase.GetKakaoId
import com.sungil.domain.useCase.LoginKAKAO
import com.sungil.domain.useCase.SetNotifyState
import com.sungil.domain.useCase.SetPermissionCheck
import com.sungil.domain.useCase.UpdateAndSaveToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getKakaoId: GetKakaoId,
    private val signUp: CheckAlreadySignUp,
    private val fcm: GetFcmToken,
    private val updateAndSaveToken: UpdateAndSaveToken,
    private val notifyState: SetNotifyState,
    private val banner: GetBanner,
    private val permissionCheck: CheckPermissionShow,
    private val setPermission: SetPermissionCheck,
    private val snsLogin: LoginKAKAO,
    private val version: CheckAppVersion,
) : ViewModel() {
    private val _actionFlow = MutableStateFlow(LoginViewState())
    val actionFlow: StateFlow<LoginViewState> = _actionFlow.asStateFlow()

    init {
        checkPermissionShow()
    }

    fun banner() {
        viewModelScope.launch {
            when (val result = banner.invoke(GetBanner.Param(BANNER))) {
                is GetBanner.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(banner = UiState.Success(result.data))
                    }
                }

                is GetBanner.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(banner = UiState.Error(result.message))
                    }

                }
            }
        }
    }

    fun version(isDebug: Boolean, appVersion: String) {
        viewModelScope.launch {
            when (val result =
                version.invoke(CheckAppVersion.Param(isDebug = isDebug, version = appVersion))) {
                is CheckAppVersion.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(appVersionCheck = UiState.Error(result.message))
                    }
                }

                CheckAppVersion.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(appVersionCheck = UiState.Success("success"))
                    }
                }
            }
        }
    }

    fun getUserId() {
        viewModelScope.launch {
            when (val result = getKakaoId.invoke()) {
                is GetKakaoId.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(userId = UiState.Success(result.kakaoId))
                    }
                }

                is GetKakaoId.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(userId = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    fun checkSignUp(kakaoId: String) {
        viewModelScope.launch {
            when (val result = signUp.invoke(CheckAlreadySignUp.Param(kakaoId))) {
                is CheckAlreadySignUp.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(signUp = UiState.Success(result.message))
                    }
                }

                is CheckAlreadySignUp.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(signUp = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    fun loginKaKao(activity: Activity, isDebug: Boolean) {
        viewModelScope.launch {
            when (val result =
                snsLogin.invoke(LoginKAKAO.Param(activity = activity, isDebug = isDebug))) {
                is LoginKAKAO.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(kakaoLogin = UiState.Success(result.snsData))
                    }
                }

                is LoginKAKAO.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(kakaoLogin = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    fun setNotification(data: Boolean) {
        viewModelScope.launch {
            when (val result = notifyState.invoke(SetNotifyState.Param(data))) {
                is SetNotifyState.Result.Success -> {
                    setPermissionCheck()
                }

                is SetNotifyState.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(notification = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    private fun checkPermissionShow() {
        viewModelScope.launch {
            when (permissionCheck.invoke(BuildConfig.NOTIFY_PERMISSION_KEY)) {
                true -> {
                    _actionFlow.update { current ->
                        current.copy(permissionShow = UiState.Success(true))
                    }
                }

                false -> {
                    _actionFlow.update { current ->
                        current.copy(permissionShow = UiState.Success(false))
                    }
                }
            }
        }
    }

    private fun setPermissionCheck() {
        viewModelScope.launch {
            when (val message = setPermission.invoke(
                SetPermissionCheck.Param(
                    key = BuildConfig.NOTIFY_PERMISSION_KEY,
                    data = true
                )
            )) {
                is SetPermissionCheck.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(notification = UiState.Success(message.message))
                    }
                }

                is SetPermissionCheck.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(notification = UiState.Error(message.errorMessage))
                    }
                }
            }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            val result = fcm.invoke()
            if (result == ERROR_FCM_TOKEN) {
                _actionFlow.update { current ->
                    current.copy(fcmToken = UiState.Error(ERROR_FCM_TOKEN))
                }
                return@launch
            }
            when (val saveFcm =
                updateAndSaveToken.invoke(UpdateAndSaveToken.Param(result))) {
                is UpdateAndSaveToken.Result.Success -> {
                    _actionFlow.update { current ->
                        current.copy(fcmToken = UiState.Success(saveFcm.message))
                    }
                }

                is UpdateAndSaveToken.Result.Fail -> {
                    _actionFlow.update { current ->
                        current.copy(fcmToken = UiState.Error(saveFcm.errorMessage))
                    }
                }
            }
        }
    }

    sealed interface UiState<out T> {
        data object Loading : UiState<Nothing>
        data class Success<T>(val data: T) : UiState<T>
        data class Error<T>(val error: String) : UiState<T>
    }

    data class LoginViewState(
        val userId: UiState<String> = UiState.Loading,
        val signUp: UiState<String> = UiState.Loading,
        val kakaoLogin: UiState<String> = UiState.Loading,
        val fcmToken: UiState<String> = UiState.Loading,
        val notification: UiState<String> = UiState.Loading,
        val banner: UiState<List<BannerData>> = UiState.Loading,
        val permissionShow: UiState<Boolean> = UiState.Loading,
        val permissionSet: UiState<Boolean> = UiState.Loading,
        val appVersionCheck: UiState<String> = UiState.Loading,
    )

}