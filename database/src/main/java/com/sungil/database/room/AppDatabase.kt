package com.sungil.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.room.model.Token
import com.sungil.database.room.model.UserInfo

@Database(
    entities = [UserInfo::class , Token :: class],
    version = 5,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun tokenDao() : TokenDao
}
