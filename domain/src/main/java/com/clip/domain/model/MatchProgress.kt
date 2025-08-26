package com.clip.domain.model

data class MatchProgress(
    val nicknameList: List<String>,
    val tmiList: List<String>,
    val nicknameOnethingMap: Map<String, String>,
)