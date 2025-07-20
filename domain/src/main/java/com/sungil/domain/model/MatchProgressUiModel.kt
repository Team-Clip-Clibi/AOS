package com.sungil.domain.model

data class MatchProgressUiModel(
    val nickName: List<String>,
    val tmi: List<String>,
    val content: List<OneThingMap>,
)

data class OneThingMap(
    val nickName: String,
    val content: String,
)