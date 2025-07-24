package com.oneThing.domain.useCase

import com.oneThing.domain.repository.DatabaseRepository
import com.oneThing.domain.repository.NetworkRepository
import javax.inject.Inject

class GetFcmToken @Inject constructor(private val repo: NetworkRepository , private val databaseRepo : DatabaseRepository) {

    suspend fun invoke(): String {
        val result = repo.getFCMToken()
        if (result == "Error") {
            val beforeToken = databaseRepo.getFcmToken()
            if(beforeToken.isEmpty()){
                return "Error to Fail Fcm"
            }
        }
        return result
    }
}