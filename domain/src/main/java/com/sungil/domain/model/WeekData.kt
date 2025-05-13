package com.sungil.domain.model

data class WeekData(
    val date : String,
    val dayOfWeek : String,
    val timeSlots: List<String>
)