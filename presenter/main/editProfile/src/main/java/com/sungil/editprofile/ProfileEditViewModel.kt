package com.sungil.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.CheckNickName
import com.sungil.domain.useCase.GetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userInfo: GetUserInfo,
    private val changeNickName: CheckNickName,
) : ViewModel() {
    private val _editProfileState = MutableStateFlow<EditProfileState>(EditProfileState.Loading)
    val editProfileState: StateFlow<EditProfileState> = _editProfileState.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }

                is GetUserInfo.Result.Success -> {
                    val data = UserInfo(
                        name = result.data.userName,
                        nickName = result.data.nickName,
                        phoneNumber = result.data.phoneNumber,
                        job = result.data.job,
                        loveState = result.data.loveState,
                        diet = result.data.diet,
                        language = result.data.language
                    )
                    _editProfileState.value = EditProfileState.Success(data)
                }
            }
        }
    }

    fun changeNickName(newNickName: String) {
        val currentState = _editProfileState.value
        if (currentState !is EditProfileState.Success) {
            _editProfileState.value = EditProfileState.Error("Fail to get Data")
            return
        }
        val updatedUserInfo = currentState.data.copy(nickName = newNickName)
        _editProfileState.value = EditProfileState.Success(updatedUserInfo)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = changeNickName.invoke(
                CheckNickName.Param(
                    newNickName
                )
            )) {
                is CheckNickName.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.message)
                }

                is CheckNickName.Result.Success -> {
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    sealed interface EditProfileState {
        data object Loading : EditProfileState
        data class Success(val data: UserInfo) : EditProfileState
        data class SuccessToChange(val message: String) : EditProfileState
        data class Error(val message: String) : EditProfileState
    }
}

data class UserInfo(
    val name: String,
    val nickName: String,
    val phoneNumber: String,
    val job: Pair<String, String>,
    val loveState: Pair<String, String>,
    val diet: String,
    val language: String,
)