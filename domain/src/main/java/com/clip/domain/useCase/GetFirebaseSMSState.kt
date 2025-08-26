package com.clip.domain.useCase

import com.clip.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirebaseSMSState @Inject constructor(private val repo: NetworkRepository) {

    suspend fun invoke(): Flow<String> = repo.collectFirebaseResult()
}