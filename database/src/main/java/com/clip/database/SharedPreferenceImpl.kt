package com.clip.database

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

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

    override suspend fun deleteKAKAOId(): Boolean {
        return try {
            preference.edit {
                remove(BuildConfig.TOKEN_KEY)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun saveSignUp(data: Boolean): Boolean {
        return try {
            preference.edit {
                putBoolean(BuildConfig.signUpKey, data)
            }
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

    override suspend fun saveFcmToken(data: String): Boolean {
        return try {
            preference.edit {
                putString(BuildConfig.fcmKey, data)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getFcmToken(): String {
        return try {
            val data = preference.getString(BuildConfig.fcmKey, "")
            if (data.isNullOrEmpty()) {
                return ""
            }
            return data
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun updateFcmToken(data: String): Boolean {
        return try {
            preference.edit { putString(BuildConfig.fcmKey, data) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun setNotificationState(data: Boolean): Boolean {
        return try {
            preference.edit { putBoolean(BuildConfig.NOTIFYKEY, data) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getNotificationState(): Boolean {
        return try {
            val data = preference.getBoolean(BuildConfig.NOTIFYKEY, false)
            return data
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun saveFirstMatchInput(): Boolean {
        return try {
            preference.edit { putBoolean(BuildConfig.MATCH_KEY, true) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getFirstMatchInput(): Boolean {
        return try {
            val data = preference.getBoolean(BuildConfig.MATCH_KEY, false)
            return data
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getPermissionShowCheck(key: String): Boolean {
        return try {
            return preference.getBoolean(key, true)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun setPermissionShowCheck(key: String, data: Boolean): Boolean {
        return try {
            preference.edit { putBoolean(key, data) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}