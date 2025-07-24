package com.oneThing.data.di

import com.oneThing.data.repositoryImpl.DatabaseRepositoryImpl
import com.oneThing.database.meetingManger.MeetingManger
import com.oneThing.database.SharedPreference
import com.oneThing.database.token.TokenManager
import com.oneThing.database.user.UserData
import com.oneThing.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseRepoModule {
    @Provides
    @Singleton
    fun provideDatabaseRepo(
        sharedPreference: SharedPreference,
        userManager: UserData,
        tokenManager: TokenManager,
        meetingManger : MeetingManger
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(sharedPreference, userManager, tokenManager , meetingManger)
    }
}