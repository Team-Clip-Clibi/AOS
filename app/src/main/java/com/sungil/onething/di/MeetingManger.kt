package com.sungil.onething.di

import com.sungil.database.MeetingManger.MeetingManger
import com.sungil.database.MeetingManger.MeetingMangerImpl
import com.sungil.database.room.dao.MeetingDao
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