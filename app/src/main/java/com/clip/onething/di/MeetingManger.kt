package com.clip.onething.di

import com.clip.database.MeetingManger.MeetingManger
import com.clip.database.MeetingManger.MeetingMangerImpl
import com.clip.database.room.dao.MeetingDao
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