package com.sungil.database

import android.content.Context
import com.google.gson.Gson
import com.sungil.database.model.TokenData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    SharedPreference {

    private val preference =
        context.getSharedPreferences(BuildConfig.SHARED_KEY, Context.MODE_PRIVATE)
    private val gson = Gson()
    override suspend fun saveToken(key: String, tokeData: TokenData): Boolean {
        return try{
            val editor = preference.edit()
            val json = gson.toJson(tokeData)
            editor.putString(key , json)
            editor.apply()
            return true
        }catch (e : Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun getToken(): TokenData {
        TODO("Not yet implemented")
    }

    override suspend fun deleteToken(key: String): Boolean {
        TODO("Not yet implemented")
    }

}