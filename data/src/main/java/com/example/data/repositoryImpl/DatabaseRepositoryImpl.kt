package com.example.data.repositoryImpl

import com.sungil.database.SharedPreference
import com.sungil.database.model.TokenData
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val database: SharedPreference) :
    DatabaseRepository {
    override suspend fun saveToken(token: String, refreshToken: String): Boolean {
        return database.saveToken(
            TokenData(
                token = token,
                refreshToken = refreshToken
            )
        )
    }

    override suspend fun getToken(): Pair<String, String> {
        return Pair("1" ," 2")
    }

    override suspend fun deleteToken(): Boolean {
      return true
    }
}