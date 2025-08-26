package com.clip.data.di

import com.clip.data.repositoryImpl.DatabaseRepositoryImpl
import com.clip.database.MeetingManger.MeetingManger
import com.clip.database.SharedPreference
import com.clip.database.token.TokenManager
import com.clip.database.user.UserData
import com.clip.domain.repository.DatabaseRepository
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