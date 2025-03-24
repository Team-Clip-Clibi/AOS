package com.sungil.database

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    SharedPreference {

    private val preference =
        context.getSharedPreferences(BuildConfig.SHARED_KEY, Context.MODE_PRIVATE)
    private val gson = Gson()
    override suspend fun saveKaKaoId(data: String): Boolean {
        return try {
            val editor = preference.edit()
            val json = gson.toJson(data)
            editor.putString(BuildConfig.TOKEN_KEY, json)
            editor.apply()
            return true
        } catch (e: Exception) {
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

    override suspend fun deleteToken(): Boolean {
        return try {
            val editor = preference.edit()
            editor.remove(BuildConfig.TOKEN_KEY)
            editor.apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun saveSignUp(data: Boolean): Boolean {
        return try {
            val editor = preference.edit()
            editor.putBoolean(BuildConfig.signUpKey, data)
            editor.apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getAlreadySignUp(): Boolean {
        return try {
            preference.getBoolean(BuildConfig.signUpKey, false)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}