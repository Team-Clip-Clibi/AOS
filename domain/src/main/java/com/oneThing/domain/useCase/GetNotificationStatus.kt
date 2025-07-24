package com.oneThing.domain.useCase

import com.oneThing.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetNotificationStatus @Inject constructor(private val database: DatabaseRepository) {

    suspend fun invoke(): Boolean {
        return database.getNotifyState()
    }
}