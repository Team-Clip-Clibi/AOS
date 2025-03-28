package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import java.security.Provider.Service
import javax.inject.Inject

class RequestSignUp @Inject constructor(
    private val database: DatabaseRepository,
    private val device: DeviceRepository,
    private val network: NetworkRepository,
) : UseCase<RequestSignUp.Param, RequestSignUp.Result> {
    data class Param(
        val termAllChecked: Boolean,
        val termServicePermission: Boolean,
        val privatePermission: Boolean,
        val marketingPermission: Boolean,
        val name: String,
        val nickName: String,
        val birtYear: String,
        val birthMonth: String,
        val birthDay: String,
        val city: String,
        val area: String,
        val gender: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val firebaseToken = database.getFcmToken()
        val socialId = database.getKaKaoId()
        if (firebaseToken.isEmpty() || socialId.isEmpty()) {
            return Result.Fail("Token is Null firebase : $firebaseToken , kakao : $socialId")
        }
        val osVersion = device.getAndroidOsVersion()
        if (osVersion.toString().isEmpty()) {
            return Result.Fail("Android os version is null")
        }
        val apiResult = network.requestSignUp(
            servicePermission = param.termServicePermission,
            privatePermission = param.privatePermission,
            marketingPermission = param.marketingPermission,
            socialId = socialId,
            osVersion = osVersion.toString(),
            firebaseToken = firebaseToken,
            isAllowNotify = param.termAllChecked
        )
        if (apiResult.first.isNullOrEmpty() || apiResult.second.isNullOrEmpty()) {
            return Result.Fail("Fail to get Token accessToken : ${apiResult.first} , refreshToken : ${apiResult.second}")
        }
        val saveResult = database.saveUserInfo(
            termAllCheck = param.termAllChecked,
            servicePermission = param.termServicePermission,
            privatePermission = param.privatePermission,
            marketingPermission = param.marketingPermission,
            name = param.name,
            nickName = param.nickName,
            birthYear = param.birtYear,
            birthMonth = param.birthMonth,
            birthDay = param.birthDay,
            city = param.city,
            area = param.area,
            gender = param.gender
        )
        if(!saveResult){
            return Result.Fail("Fail to save UserInfo")
        }
        return Result.Success("SignUp Success")
    }

}