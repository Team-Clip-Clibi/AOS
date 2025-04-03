package com.sungil.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sungil.database.room.model.UserInfo

@Dao
interface UserInfoDao {

    // 삽입 또는 업데이트
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(param: UserInfo)

    // 단일 조회: name 기준
    @Query("SELECT * FROM userinfo WHERE name = :name")
    suspend fun getByName(name: String): UserInfo?

    // 전체 조회
    @Query("SELECT * FROM userinfo")
    suspend fun getAll(): List<UserInfo>

    // name 기준 삭제
    @Query("DELETE FROM userinfo WHERE name = :name")
    suspend fun deleteByName(name: String)

    // 전체 삭제
    @Query("DELETE FROM userinfo")
    suspend fun deleteAll()


}