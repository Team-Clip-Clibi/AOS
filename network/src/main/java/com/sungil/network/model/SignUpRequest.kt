package com.sungil.network.model

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("servicePermission") val servicePermission: Boolean,
    @SerializedName("privatePermission") val privatePermission: Boolean,
    @SerializedName("marketingPermission") val marketingPermission: Boolean,
    @SerializedName("socialId") val socialId: String,
    @SerializedName("platform") val platform: String = "KAKAO",
    @SerializedName("deviceType") val deviceType: String = "ANDROID",
    @SerializedName("osVersion") val osVersion: String,
    @SerializedName("firebaseToken") val firebaseToken: String,
    @SerializedName("isAllowNotify") val isAllowNotify: Boolean,
)