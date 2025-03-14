package com.sungil.database

import android.content.Context
import com.sungil.database.model.TokenData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    SharedPreference {

    override suspend fun saveToken(key: String, tokeData: TokenData): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(): TokenData {
        TODO("Not yet implemented")
    }

    override suspend fun deleteToken(key: String): Boolean {
        TODO("Not yet implemented")
    }
}