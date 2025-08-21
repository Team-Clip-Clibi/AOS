package com.sungil.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sungil.domain.model.BannerData
import com.sungil.domain.model.HomeBanner
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchDetail
import com.sungil.domain.model.MatchProgressUiModel
import com.sungil.domain.model.MatchTrigger
import com.sungil.domain.model.NotWriteReview
import com.sungil.domain.model.NotificationData
import com.sungil.domain.model.OneThineNotify
import com.sungil.domain.model.Participants
import com.sungil.domain.model.UserData
import com.sungil.domain.useCase.GetBanner
import com.sungil.domain.useCase.GetFirstMatchInput
import com.sungil.domain.useCase.GetHomeBanner
import com.sungil.domain.useCase.GetLatestMatch
import com.sungil.domain.useCase.GetMatch
import com.sungil.domain.useCase.GetMatchDetail
import com.sungil.domain.useCase.GetMatchNotice
import com.sungil.domain.useCase.GetMatchingData
import com.sungil.domain.useCase.GetNewNotification
import com.sungil.domain.useCase.GetNotWriteReview
import com.sungil.domain.useCase.GetNotification
import com.sungil.domain.useCase.GetNotificationStatus
import com.sungil.domain.useCase.GetParticipants
import com.sungil.domain.useCase.GetProgressMatchInfo
import com.sungil.domain.useCase.GetUserInfo
import com.sungil.domain.useCase.MonitoryMeetingTime
import com.sungil.domain.useCase.SendLateMatch
import com.sungil.domain.useCase.SendMatchReview
import com.sungil.domain.useCase.SetNotifyState
import com.sungil.domain.useCase.SetPermissionCheck
import com.sungil.domain.useCase.WriteReviewLater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userInfo: GetUserInfo,
    private val notify: GetNotification,
    private val oneThingNotify: GetNewNotification,
    private val match: GetMatch,
    private val banner: GetBanner,
    private val firstMatch: GetFirstMatchInput,
    private val latestDay: GetLatestMatch,
    private val matching: GetMatchingData,
    private val matchDetail: GetMatchDetail,
    private val matchNotice: GetMatchNotice,
    private val sendLate: SendLateMatch,
    private val sendReview: SendMatchReview,
    private val participants: GetParticipants,
    private val meetTime: MonitoryMeetingTime,
    private val progressMatch: GetProgressMatchInfo,
    private val alarmStatus: GetNotificationStatus,
    private val setAlarmStatus: SetNotifyState,
    private val setPermission: SetPermissionCheck,
    private val homeBanner: GetHomeBanner,
    private val notWriteReview: GetNotWriteReview,
    private val reviewLater : WriteReviewLater
) : ViewModel() {

    private val _userState = MutableStateFlow(MainViewState())
    val userState: StateFlow<MainViewState> = _userState.asStateFlow()

    private val _meetingTrigger = MutableStateFlow<MatchTriggerUiState>(MatchTriggerUiState.Idle)
    val meetingTrigger: StateFlow<MatchTriggerUiState> = _meetingTrigger.asStateFlow()

    val matchAllData = matching.invoke(matchingStatus = MATCH_KEY_ALL, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchApplied = matching.invoke(matchingStatus = MATCH_KEY_APPLIED, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchConfirmed = matching.invoke(matchingStatus = MATCH_KEY_CONFIRMED, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchComplete = matching.invoke(matchingStatus = MATCH_KEY_COMPLETED, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchCancelled = matching.invoke(matchingStatus = MATCH_KEY_CANCELLED, lastMeetingTime = "")
        .cachedIn(viewModelScope)

    val notice = matchNotice.invoke(lastTime = "")
        .cachedIn(viewModelScope)

    private var _matchButton = MutableStateFlow(0)
    val matchButton: StateFlow<Int> = _matchButton.asStateFlow()

    private var _bottomSheetButton = MutableStateFlow(BottomSheetView.MATCH_START_HELLO_VIEW)
    val bottomSheetButton: StateFlow<BottomSheetView> = _bottomSheetButton.asStateFlow()

    private var _bottomSheetViewShow = MutableStateFlow(false)
    val bottomSheetShow: StateFlow<Boolean> = _bottomSheetViewShow.asStateFlow()

    private var _reviewDialogShow = MutableStateFlow(false)
    val reviewDialog: StateFlow<Boolean> = _reviewDialogShow.asStateFlow()

    private var _lightDialogShow = MutableStateFlow(false)
    val lightDialogShow: StateFlow<Boolean> = _lightDialogShow.asStateFlow()

    private val _actionInFlight = MutableStateFlow(false)
    val actionInFlight = _actionInFlight.asStateFlow()
    private var participantsData: List<String> = emptyList()
    private var matchId: Int = 0
    private var matchType: String = ""

    private var _dialogIndex = MutableStateFlow(0)
    val dialogIndex = _dialogIndex.asStateFlow()


    init {
        requestUserInfo()
        oneThingNotify()
        serviceNotify()
        requestMatch()
        getBanner()
        getLatestMatch()
        alarm()
        homeBanner()
        notWriteReview()
    }

    fun requestUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Success -> {
                    _userState.update { state ->
                        state.copy(userDataState = UiState.Success(result.data))
                    }
                }

                is GetUserInfo.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(userDataState = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }


    fun oneThingNotify() {
        viewModelScope.launch {
            when (val result = oneThingNotify.invoke()) {
                is GetNewNotification.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(oneThingState = UiState.Error(result.errorMessage))
                    }

                }

                is GetNewNotification.Result.Success -> {
                    _userState.update { state ->
                        state.copy(oneThingState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    fun serviceNotify() {
        viewModelScope.launch {
            when (val result = notify.invoke()) {
                is GetNotification.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(notificationResponseState = UiState.Error(result.errorMessage))
                    }
                }

                is GetNotification.Result.Success -> {
                    _userState.update { state ->
                        state.copy(notificationResponseState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    fun requestMatch() {
        viewModelScope.launch {
            when (val result = match.invoke()) {
                is GetMatch.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(matchState = UiState.Error(result.errorMessage))
                    }
                }

                is GetMatch.Result.Success -> {
                    _userState.update { state ->
                        state.copy(matchState = UiState.Success(result.data))
                    }
                    startMonitoringMatch(result.data)
                }
            }
        }
    }

    private fun startMonitoringMatch(data: MatchData) {
        viewModelScope.launch {
            meetTime.invoke(MonitoryMeetingTime.Param(data))
                .collect { data ->
                    _meetingTrigger.value = MatchTriggerUiState.Triggered(data)
                }
        }
    }

    fun getLatestMatch() {
        viewModelScope.launch {
            when (val result = latestDay.invoke()) {
                is GetLatestMatch.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(latestDay = UiState.Error(result.errorMessage))
                    }
                }

                is GetLatestMatch.Result.Success -> {
                    _userState.update { state ->
                        state.copy(
                            latestDay = UiState.Success(
                                LatestDayInfo(
                                    time = result.time,
                                    applyTime = result.applyTime,
                                    confirmTime = result.confirmTime
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getBanner() {
        viewModelScope.launch {
            when (val result = banner.invoke(GetBanner.Param("HOME"))) {
                is GetBanner.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(banner = UiState.Error(result.message))
                    }
                }

                is GetBanner.Result.Success -> {
                    _userState.update { state ->
                        state.copy(banner = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    fun checkFirstMatch(orderType: String) {
        viewModelScope.launch {
            when (firstMatch.invoke()) {
                is GetFirstMatchInput.Result.Success -> {
                    _userState.update { state ->
                        state.copy(
                            firstMatch = UiState.Success(orderType)
                        )
                    }
                }

                is GetFirstMatchInput.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(
                            firstMatch = UiState.Error(orderType)
                        )
                    }
                }
            }
        }
    }

    fun matchDetail(matchId: Int, matchType: String) {
        viewModelScope.launch {
            when (val result = matchDetail.invoke(
                GetMatchDetail.Param(
                    matchId = matchId,
                    matchType = matchType
                )
            )) {
                is GetMatchDetail.Result.Success -> {
                    _userState.update { state ->
                        state.copy(matchDetail = UiState.Success(result.matchDetail))
                    }
                }

                is GetMatchDetail.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(matchDetail = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    fun sendLate(matchType: String, lateTime: Int, matchId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = sendLate.invoke(
                SendLateMatch.Param(
                    id = matchId,
                    lateTime = lateTime,
                    matchType = matchType
                )
            )) {
                is SendLateMatch.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(matchLate = UiState.Error(result.errorMessage))
                    }
                }

                is SendLateMatch.Result.Success -> {
                    _userState.update { state ->
                        state.copy(
                            matchLate = UiState.Success(
                                MatchLate(
                                    matchId = result.id,
                                    time = result.lateTime
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun getParticipants(matchId: Int, matchType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = participants.invoke(
                GetParticipants.Param(
                    matchId = matchId,
                    matchType = matchType
                )
            )) {
                is GetParticipants.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(participants = UiState.Error(result.errorMessage))
                    }
                }

                is GetParticipants.Result.Success -> {
                    _userState.update { state ->
                        state.copy(
                            participants = UiState.Success(
                                Participant(
                                    person = result.person,
                                    matchId = matchId,
                                    matchType = matchType
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun progressMatchInfo(matchId: Int, matchType: String) {
        val currentState = _userState.value.progressMatchInfo
        if (currentState is UiState.Success) {
            showBottomSheet()
            return
        }
        viewModelScope.launch {
            when (val result = progressMatch.invoke(
                GetProgressMatchInfo.Param(
                    matchId = matchId,
                    matchType = matchType
                )
            )) {
                is GetProgressMatchInfo.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(progressMatchInfo = UiState.Error(result.errorMessage))
                    }
                }

                is GetProgressMatchInfo.Result.Success -> {
                    _userState.update { state ->
                        state.copy(progressMatchInfo = UiState.Success(result.data))
                    }
                    showBottomSheet()
                }
            }
        }
    }

    fun homeBanner() {
        viewModelScope.launch {
            when (val result = homeBanner.invoke()) {
                is GetHomeBanner.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(homeBanner = UiState.Error(result.errorMessage))
                    }
                }

                is GetHomeBanner.Result.Success -> {
                    _userState.update { state ->
                        state.copy(homeBanner = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    fun plusHomeBanner() {
        _actionInFlight.value = false
        val list = (_userState.value.notWriteReview as? UiState.Success)?.data ?: return
        if (list.isEmpty()) return
        if (list.size <= _dialogIndex.value + 1) return
        _dialogIndex.value += 1
        _userState.update { state ->
            state.copy(
                participants = UiState.Loading,
                writeReviewLater = UiState.Loading
            )
        }
        _actionInFlight.value = true
    }

    fun initReviewLater(){
        _userState.update { state ->
            state.copy(writeReviewLater = UiState.Loading)
        }
    }

    fun reviewLater(matchId: Int, matchType: String) {
        viewModelScope.launch {
            when (val result = reviewLater.invoke(
                WriteReviewLater.Param(
                    matchId = matchId,
                    matchType = matchType
                )
            )) {
                is WriteReviewLater.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(writeReviewLater = UiState.Error(result.errorMessage))
                    }
                }

                is WriteReviewLater.Result.Success -> {
                    _userState.update { state ->
                        state.copy(writeReviewLater = UiState.Success("Success"))
                    }
                }
            }
        }
    }

    private fun notWriteReview() {
        viewModelScope.launch {
            when (val result = notWriteReview.invoke()) {
                is GetNotWriteReview.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(notWriteReview = UiState.Error(result.errorMessage))
                    }
                }

                is GetNotWriteReview.Result.Success -> {
                    _userState.update { state ->
                        state.copy(notWriteReview = UiState.Success(result.data))
                    }
                    _dialogIndex.value = 0
                    _actionInFlight.value = true
                }
            }
        }
    }

    private fun alarm() {
        viewModelScope.launch {
            val result = alarmStatus.invoke()
            _userState.update { state ->
                state.copy(alarmStatus = UiState.Success(result))
            }
        }
    }

    fun changeAlarm(data: Boolean) {
        viewModelScope.launch {
            when (val result = setAlarmStatus.invoke(SetNotifyState.Param(data))) {
                is SetNotifyState.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(alarmStatus = UiState.Error(result.errorMessage))
                    }
                }

                is SetNotifyState.Result.Success -> {
                    setAlarmStatus(data)
                }
            }
        }
    }

    private fun setAlarmStatus(data: Boolean) {
        viewModelScope.launch {
            when (val result = setPermission.invoke(
                SetPermissionCheck.Param(
                    key = BuildConfig.NOTIFY_PERMISSION_KEY,
                    data = data
                )
            )) {
                is SetPermissionCheck.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(alarmStatus = UiState.Error(result.errorMessage))
                    }
                }

                is SetPermissionCheck.Result.Success -> {
                    _userState.update { state ->
                        state.copy(alarmStatus = UiState.Success(data))
                    }
                }
            }
        }
    }

    fun initParticipants() {
        _userState.update { state ->
            state.copy(
                participants = UiState.Loading,
                reviewDetail = "",
                allAttend = true,
                badReviewItem = arrayListOf(),
                goodReviewItem = arrayListOf(),
                reviewButton = REVIEW_SELECT_NOTHING,
                writeReview = UiState.Loading,
                unAttendMember = arrayListOf(),
            )
        }
    }

    fun setReviewItem(buttonNumber: Int) {
        _userState.update { state ->
            state.copy(reviewButton = buttonNumber)
        }
    }

    fun setBadItem(data: String) {
        val currentItem = _userState.value
        val currentList = currentItem.badReviewItem.toMutableList()
        if (currentList.contains(data)) {
            currentList.remove(data)
        } else {
            currentList.add(data)
        }
        _userState.update { state ->
            state.copy(badReviewItem = ArrayList(currentList))
        }
    }

    fun setGoodItem(data: String) {
        val currentItem = _userState.value
        val list = currentItem.goodReviewItem.toMutableList()
        if (list.contains(data)) {
            list.remove(data)
        } else {
            list.add(data)
        }
        _userState.update { state ->
            state.copy(goodReviewItem = ArrayList(list))
        }
    }

    fun reviewDetail(data: String) {
        _userState.update { state ->
            state.copy(reviewDetail = data)
        }
    }

    fun setAllAttend(data: Boolean) {
        _userState.update { state ->
            state.copy(allAttend = data)
        }
    }

    fun setMatchButton(index: Int) {
        _matchButton.value = index
    }

    fun setMatchDetailInit() {
        _userState.update { state ->
            state.copy(matchDetail = UiState.Loading)
        }
    }

    fun setUnAttendMember(data: String) {
        val currentItem = _userState.value
        val list = currentItem.unAttendMember.toMutableList()
        if (list.contains(data)) {
            list.remove(data)
        } else {
            list.add(data)
        }
        _userState.update { state ->
            state.copy(unAttendMember = ArrayList(list))
        }
    }

    fun sendReview(matchId: Int, matchType: String) {
        val state = _userState.value
        val reviewButton = ReviewIcon.fromButtonInt(state.reviewButton)
        val badReviewItem = state.badReviewItem.joinToString(separator = ", ")
        val goodReviewItem = state.goodReviewItem.joinToString(separator = ", ")
        val reviewDetail = state.reviewDetail
        val allAttend = state.allAttend
        val unAttendMember = state.unAttendMember.joinToString(separator = ", ")
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = sendReview.invoke(
                SendMatchReview.Param(
                    allAttend = allAttend,
                    matchType = matchType,
                    matchId = matchId,
                    mood = reviewButton,
                    negativePoints = badReviewItem,
                    noShowMembers = unAttendMember,
                    positivePoints = goodReviewItem,
                    reviewContent = reviewDetail
                )
            )) {
                is SendMatchReview.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(writeReview = UiState.Error(result.errorMessage))
                    }
                }

                is SendMatchReview.Result.Success -> {
                    _userState.update { state ->
                        state.copy(writeReview = UiState.Success(result.matchId))
                    }
                }
            }
        }
    }

    fun setBottomSheetButton(data: BottomSheetView) {
        _bottomSheetButton.value = data
    }

    fun initBottomSheetButton() {
        _bottomSheetButton.value = BottomSheetView.MATCH_START_HELLO_VIEW
        _bottomSheetViewShow.value = false
    }

    private fun showBottomSheet() {
        _bottomSheetViewShow.value = true
    }

    fun closeReviewDialog() {
        _reviewDialogShow.value = false
    }

    fun showReviewDialog() {
        _reviewDialogShow.value = true
    }

    fun closeLightDialog() {
        _lightDialogShow.value = false
    }

    fun showLightDialog() {
        _lightDialogShow.value = true
    }

    fun setReviewData(
        participants: List<String>,
        matchId: Int,
        matchType: String,
    ) {
        this.participantsData = participants
        this.matchId = matchId
        this.matchType = matchType
    }

    fun getReviewData(): ReviewData {
        return ReviewData(
            participants = participantsData,
            matchId = matchId,
            matchType = matchType
        )
    }

    fun initProgressMatch() {
        _userState.value.progressMatchInfo = UiState.Loading
    }

    fun removeHomeBannerData(id: Int) {
        _userState.update { state ->
            val next = when (val hbData = state.homeBanner) {
                is UiState.Success -> UiState.Success(hbData.data.filterNot { banner -> banner.id == id })
                is UiState.Error -> hbData
                UiState.Loading -> hbData
            }
            state.copy(homeBanner = next)
        }
    }

    sealed interface UiState<out T> {

        data object Loading : UiState<Nothing>
        data class Success<T>(val data: T) : UiState<T>
        data class Error(val message: String) : UiState<Nothing>
    }

    sealed interface MatchTriggerUiState {
        data object Idle : MatchTriggerUiState
        data class Triggered(val dto: MatchTrigger) : MatchTriggerUiState
    }

    data class MainViewState(
        val userDataState: UiState<UserData> = UiState.Loading,
        val notificationResponseState: UiState<List<NotificationData>> = UiState.Loading,
        val oneThingState: UiState<List<OneThineNotify>> = UiState.Loading,
        val banner: UiState<List<BannerData>> = UiState.Loading,
        val matchState: UiState<MatchData> = UiState.Loading,
        val firstMatch: UiState<String> = UiState.Loading,
        val latestDay: UiState<LatestDayInfo> = UiState.Loading,
        val matchDetail: UiState<MatchDetail> = UiState.Loading,
        val matchLate: UiState<MatchLate> = UiState.Loading,
        val participants: UiState<Participant> = UiState.Loading,
        val alarmStatus: UiState<Boolean> = UiState.Loading,
        val reviewButton: Int = REVIEW_SELECT_NOTHING,
        val badReviewItem: ArrayList<String> = arrayListOf(),
        val goodReviewItem: ArrayList<String> = arrayListOf(),
        val reviewDetail: String = "",
        val allAttend: Boolean = true,
        val unAttendMember: ArrayList<String> = arrayListOf(),
        val writeReview: UiState<Int> = UiState.Loading,
        var progressMatchInfo: UiState<MatchProgressUiModel> = UiState.Loading,
        var homeBanner: UiState<List<HomeBanner>> = UiState.Loading,
        var notWriteReview: UiState<ArrayList<NotWriteReview>> = UiState.Loading,
        var writeReviewLater : UiState<String> = UiState.Loading
    )

}

data class Participant(
    val person: List<Participants>,
    val matchId: Int,
    val matchType: String,
)

data class LatestDayInfo(
    val time: String, val applyTime: Int, val confirmTime: Int,
)

data class MatchLate(
    val time: Int,
    val matchId: Int,
)

data class ReviewData(
    val participants: List<String>,
    val matchId: Int,
    val matchType: String,
)
