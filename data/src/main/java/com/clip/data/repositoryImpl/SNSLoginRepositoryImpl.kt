package com.clip.data.repositoryImpl

import android.app.Activity
import auth.SNSLogin
import com.clip.domain.repository.SNSLoginRepository
import javax.inject.Inject

class SNSLoginRepositoryImpl @Inject constructor(private val snsLogin: SNSLogin) :
    SNSLoginRepository {
    override suspend fun checkKAKAOLogin(activity: Activity): Boolean {
        return snsLogin.checkKAKAOLogin(activity)
    }

    override suspend fun loginKAKOWeb(activity: Activity): String {
        return snsLogin.kakaoWebLogin(activity).fold(
            onSuccess = { data -> data },
            onFailure = { error -> "error :${error.message}" }
        )
    }

    override suspend fun loginKAKAOSdk(activity: Activity): String {
        return snsLogin.kakaoSdkLogin(activity).fold(
            onSuccess = { data -> data },
            onFailure = { error -> "error :${error.message}" }
        )
    }
}