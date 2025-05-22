package com.sungil.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sungil.database.TABLE_USER_INFO

@Entity(tableName = TABLE_USER_INFO)
data class UserInfo(
    @PrimaryKey val name: String,
    val nickName: String,
    val platform : String,
    val phoneNumber : String,
    val job : String,
    val myLoveState : String,
    val wantLoveState : Boolean,
    val diet : String,
    val language : String
)