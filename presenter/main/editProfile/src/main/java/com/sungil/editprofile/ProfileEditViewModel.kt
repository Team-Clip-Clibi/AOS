package com.sungil.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.ActivateHaptic
import com.sungil.domain.useCase.CheckNickName
import com.sungil.domain.useCase.GetUserInfo
import com.sungil.domain.useCase.LogOut
import com.sungil.domain.useCase.SaveChangeProfileData
import com.sungil.domain.useCase.SignOut
import com.sungil.domain.useCase.UpdateDiet
import com.sungil.domain.useCase.UpdateJob
import com.sungil.domain.useCase.UpdateLanguage
import com.sungil.domain.useCase.UpdateLove
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
    private val userInfoUseCase: GetUserInfo,
    private val changeNickName: CheckNickName,
    private val haptic: ActivateHaptic,
    private val changeJob: UpdateJob,
    private val saveData: SaveChangeProfileData,
    private val updateLoveState: UpdateLove,
    private val updateLanguage: UpdateLanguage,
    private val logout: LogOut,
    private val signOut: SignOut,
    private val diet : UpdateDiet
) : ViewModel() {

    private val _editProfileState = MutableStateFlow<EditProfileState>(EditProfileState.Loading)
    val editProfileState: StateFlow<EditProfileState> = _editProfileState.asStateFlow()

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    private val _selectedJobs = MutableStateFlow<List<JOB>>(emptyList())
    val selectedJobs: StateFlow<List<JOB>> = _selectedJobs.asStateFlow()

    private val _jobSelectionError = MutableStateFlow(false)
    val jobSelectionError: StateFlow<Boolean> = _jobSelectionError.asStateFlow()
    private var _initialJobSelection: List<JOB> = emptyList()

    private val _loveState = MutableStateFlow(LoveState())
    val loveState: StateFlow<LoveState> = _loveState

    private var _button = MutableStateFlow(false)
    val button: StateFlow<Boolean> = _button

    private var _language = MutableStateFlow(LANGUAGE.KOREAN)
    val language: StateFlow<LANGUAGE> = _language

    private var _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog

    private var _signOutContent = MutableStateFlow(SignOutData.NOTING)
    val signOutContent: StateFlow<SignOutData> = _signOutContent

    private var _dietData = MutableStateFlow(DIET.NONE)
    val dietData : StateFlow<DIET> = _dietData

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            when (val result = userInfoUseCase.invoke()) {
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
                    _userInfo.value = data

                    _initialJobSelection = JOB.entries.filter {
                        it.displayName == data.job.first || it.displayName == data.job.second
                    }
                    _selectedJobs.value = _initialJobSelection

                    _loveState.value = LoveState(
                        love = LOVE.entries.find { it.name == data.loveState.first } ?: LOVE.SINGLE,
                        Meeting = if (data.loveState.second == "SAME") MEETING.SAME else MEETING.OKAY
                    )
                    _language.value = LANGUAGE.entries.find { it.name == data.language } ?: LANGUAGE.KOREAN
                    _dietData.value = DIET.entries.find { it.displayName == data.diet } ?: DIET.NONE
                    _editProfileState.value = EditProfileState.Loading // 초기 상태로 설정 (UI가 이 상태를 기준으로 판단한다면 유지)
                }
            }
        }
    }

    fun changeNickName(newNickName: String) {
        if (_userInfo.value == null) {
            _editProfileState.value = EditProfileState.Error("사용자 정보를 불러올 수 없습니다.")
            return
        }

        _userInfo.update { it?.copy(nickName = newNickName) }
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = changeNickName.invoke(CheckNickName.Param(newNickName))) {
                is CheckNickName.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.message)
                }

                is CheckNickName.Result.Success -> {
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    fun changeJob() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = changeJob.invoke(
                UpdateJob.Param(
                    Pair(_selectedJobs.value[0].name, _selectedJobs.value[1].name)
                )
            )) {
                is UpdateJob.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }

                is UpdateJob.Result.Success -> {
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    fun changeLanguage() {
        viewModelScope.launch {
            when (val result = updateLanguage.invoke(
                UpdateLanguage.Param(_language.value.toString())
            )) {
                is UpdateLanguage.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }

                is UpdateLanguage.Result.Success -> {
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    fun sendLoveState() {
        viewModelScope.launch {
            when (val result = updateLoveState.invoke(
                UpdateLove.Param(
                    _loveState.value.love.toString(),
                    _loveState.value.Meeting.toString()
                )
            )) {
                is UpdateLove.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }

                is UpdateLove.Result.Success -> {
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            when (val result = logout.invoke()) {
                is LogOut.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }

                is LogOut.Result.Success -> {
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = signOut.invoke()) {
                is SignOut.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }

                is SignOut.Result.Success -> {
                    _editProfileState.value = EditProfileState.GoodBye(result.message)
                }
            }
        }
    }

    fun updateDiet(){
        viewModelScope.launch {
            when(val result = diet.invoke(UpdateDiet.Param(_dietData.value.displayName))){
                is UpdateDiet.Result.Fail -> {
                    _editProfileState.value = EditProfileState.Error(result.errorMessage)
                }
                is UpdateDiet.Result.Success ->{
                    _editProfileState.value = EditProfileState.SuccessToChange(result.message)
                }
            }
        }
    }

    fun isJobSelectionChanged(): Boolean {
        return _selectedJobs.value.toSet() != _initialJobSelection.toSet()
    }

    fun disMissDialog() {
        _showLogoutDialog.value = false
    }

    fun setDialogTrue() {
        _showLogoutDialog.value = true
    }

    fun changeLoveState(data: LOVE) {
        _loveState.update { it.copy(love = data) }
        _button.value = true
    }

    fun changeMeetingState(data: MEETING) {
        _loveState.update { it.copy(Meeting = data) }
        _button.value = true
    }

    fun changeLanguage(data: LANGUAGE) {
        _language.value = data
        _button.value = true
    }

    fun changeSignOutContent(data: SignOutData) {
        _signOutContent.value = data
        _button.value = true
    }

    fun setDiet(data : DIET){
        _dietData.value = data
    }

    fun initFlow() {
        _editProfileState.value = EditProfileState.Loading
        _button.value = false
        _showLogoutDialog.value = false

    }

    fun toggleJob(job: JOB) {
        val current = _selectedJobs.value
        val newList = when {
            job in current -> {
                _jobSelectionError.value = false
                current - job
            }

            current.size < 2 -> {
                _jobSelectionError.value = false
                current + job
            }

            else -> {
                _jobSelectionError.value = true
                viewModelScope.launch { haptic.invoke() }
                return
            }
        }
        _selectedJobs.value = newList
    }

    sealed interface EditProfileState {
        object Loading : EditProfileState
        data class SuccessToChange(val message: String) : EditProfileState
        data class GoodBye(val message: String) : EditProfileState
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

data class LoveState(
    var love: LOVE = LOVE.SINGLE,
    var Meeting: MEETING = MEETING.SAME,
)
