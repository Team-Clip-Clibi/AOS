package com.sungil.main.model

import com.sungil.domain.model.MatchData

data class MatchResultData(
    val saveData: MatchData,        // 서버 + DB 공통
    val unSaveData: MatchData   // 서버에만 있는 데이터
)