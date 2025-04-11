package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Report(
    val content: String,
    val reportCategory: SLANG,
)


enum class SLANG {
    SLANG,
    CRIME,
    SEX,
    FALSE,
    ABUSING,
    ETC
}
