package com.clip.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clip.database.TABLE_MEETING

@Entity(tableName = TABLE_MEETING)
data class Meeting(
    @PrimaryKey val matchId : Int,
    val category : String,
    val time : String,
    val location : String
)