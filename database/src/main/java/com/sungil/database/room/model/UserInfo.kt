package com.sungil.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sungil.database.TABLE_USER_INFO

@Entity(tableName = TABLE_USER_INFO)
data class UserInfo(
    @PrimaryKey val name: String,
    val marketingPermission: Boolean,
    val nickName: String,
    val birtYear: String,
    val birthMonth: String,
    val birthDay: String,
    val city: String,
    val area: String,
    val gender: String,
    val platform : String,
    val phoneNumber : String,
    val job : List<String>,
    val loveState : List<String>,
    val diet : String,
    val language : String
)