package com.example.data.repositoryImpl

import android.app.Activity
import com.sungil.domain.repository.SNSLoginRepositoryImpl
import javax.inject.Inject

class SNSLoginRepositoryImplImpl @Inject constructor() :
    SNSLoginRepositoryImpl {
    override suspend fun loginKAKOWeb(activity: Activity): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun loginKAKAOSdk(activity: Activity): Result<String> {
        TODO("Not yet implemented")
    }
}