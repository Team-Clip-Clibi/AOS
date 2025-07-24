package com.oneThing.onething.di

import com.oneThing.database.meetingManger.MeetingManger
import com.oneThing.database.meetingManger.MeetingMangerImpl
import com.oneThing.database.room.dao.MeetingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MeetingManger {
    @Provides
    @Singleton
    fun provideMeetingManger(meetingDao: MeetingDao) : MeetingManger{
        return MeetingMangerImpl(meetingDao)
    }
}