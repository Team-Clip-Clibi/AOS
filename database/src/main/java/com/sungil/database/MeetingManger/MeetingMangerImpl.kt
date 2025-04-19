package com.sungil.database.MeetingManger

import com.sungil.database.room.dao.MeetingDao
import com.sungil.database.room.model.Meeting
import javax.inject.Inject
class MeetingMangerImpl @Inject constructor(
    private val meetingDao: MeetingDao
) : MeetingManger {

    private var cache: MutableList<Meeting>? = null

    override suspend fun inset(
        matchId: Int,
        time: String,
        category: String,
        location: String
    ): Boolean {
        val newMeeting = Meeting(matchId, time, category, location)
        return try {
            meetingDao.insert(newMeeting)
            cache?.add(newMeeting)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun get(): List<Meeting> {
        return cache ?: emptyList()
    }

    override suspend fun delete(matchId: Int): Boolean {
        return try {
            meetingDao.deleteByMatchId(matchId)
            cache?.removeAll { it.matchId == matchId }
            true
        } catch (e: Exception) {
            false
        }
    }
}
