package com.clip.database.MeetingManger

import com.clip.database.room.model.Meeting

interface MeetingManger {
    suspend fun inset(matchId : Int , time : String , category : String , location : String) : Boolean
    suspend fun get() : List<Meeting>
    suspend fun delete(matchId: Int) : Boolean
}