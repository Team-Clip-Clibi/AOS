package com.sungil.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sungil.database.TABLE_TOKEN

@Entity(tableName = TABLE_TOKEN)
data class Token(
    @PrimaryKey val refreshToken: String,
    val accessToken: String,
)