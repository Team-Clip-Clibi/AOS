package com.oneThing.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oneThing.database.room.model.Token

@Dao
interface TokenDao {
    @Query("SELECT * FROM Token LIMIT 1")
    suspend fun getToken(): Token?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: Token)

    @Delete
    suspend fun deleteToken(token: Token)

    @Query("DELETE FROM Token")
    suspend fun deleteAll()
}