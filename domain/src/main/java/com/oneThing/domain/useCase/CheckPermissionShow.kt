package com.oneThing.domain.useCase

import com.oneThing.domain.repository.DatabaseRepository
import javax.inject.Inject

class CheckPermissionShow @Inject constructor(private val database: DatabaseRepository) {

    suspend fun invoke(key: String): Boolean {
        return database.getPermissionCheck(key = key)
    }
}