package com.sungil.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("servicePermission") val servicePermission: Boolean,
    @SerialName("privatePermission") val privatePermission: Boolean,
    @SerialName("marketingPermission") val marketingPermission: Boolean,
    @SerialName("socialId") val socialId: String,
    @SerialName("platform") val platform: String = "KAKAO",
    @SerialName("deviceType") val deviceType: String = "ANDROID",
    @SerialName("osVersion") val osVersion: String,
    @SerialName("firebaseToken") val firebaseToken: String,
    @SerialName("isAllowNotify") val isAllowNotify: Boolean
)
