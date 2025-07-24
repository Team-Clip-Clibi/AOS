package com.oneThing.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oneThing.database.room.model.UserInfo

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userInfo: UserInfo)

    @Query("SELECT * FROM userinfo LIMIT 1")
    suspend fun getUserInfo(): UserInfo?

    @Query("DELETE FROM userinfo")
    suspend fun deleteAll()

}