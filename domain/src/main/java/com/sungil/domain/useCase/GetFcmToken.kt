package com.sungil.domain.useCase

import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
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