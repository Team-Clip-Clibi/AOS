package com.clip.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clip.database.room.dao.MeetingDao
import com.clip.database.room.dao.TokenDao
import com.clip.database.room.dao.UserInfoDao
import com.clip.database.room.model.Meeting
import com.clip.database.room.model.Token
import com.clip.database.room.model.UserInfo

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
