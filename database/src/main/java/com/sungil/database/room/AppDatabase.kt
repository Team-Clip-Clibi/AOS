package com.sungil.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sungil.database.room.dao.MeetingDao
import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.room.model.Meeting
import com.sungil.database.room.model.Token
import com.sungil.database.room.model.UserInfo

@Database(
    entities = [UserInfo::class , Token :: class , Meeting :: class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun tokenDao() : TokenDao
    abstract fun meetingDao() : MeetingDao
}
