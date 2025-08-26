package com.clip.domain.useCase

import com.clip.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetNotificationStatus @Inject constructor(private val database: DatabaseRepository) {

    suspend fun invoke(): Boolean {
        return database.getNotifyState()
    }
}