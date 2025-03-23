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
    override suspend fun saveKaKaoId(data : String): Boolean {
        return try{
            val editor = preference.edit()
            val json = gson.toJson(data)
            editor.putString(BuildConfig.TOKEN_KEY , json)
            editor.apply()
            return true
        }catch (e : Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun getKaKaoId(): String {
        return try {
            val json = preference.getString(BuildConfig.TOKEN_KEY, null) ?: return ""
            json.let { gson.fromJson(it, String::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun deleteToken(key: String): Boolean {
        TODO("Not yet implemented")
    }

}