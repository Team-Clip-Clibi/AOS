package com.example.data.repositoryImpl

import com.sungil.database.SharedPreference
import com.sungil.database.model.TokenData
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val database: SharedPreference) :
    DatabaseRepository {
    override suspend fun saveToken(token: String): Boolean {
        return database.saveToken(
            token
        )
    }

    override suspend fun getToken(): Pair<String, String> {
        return Pair("", "")
    }

    override suspend fun deleteToken(): Boolean {
        return true
    }
}