package com.sungil.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sungil.database.TABLE_MEETING
import com.sungil.database.room.model.Meeting

@Dao
interface MeetingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Meeting)

    @Query("SELECT * FROM $TABLE_MEETING")
    suspend fun getAll(): List<Meeting>?

    @Query("DELETE FROM $TABLE_MEETING WHERE matchId = :matchId")
    suspend fun deleteByMatchId(matchId: Int)
}