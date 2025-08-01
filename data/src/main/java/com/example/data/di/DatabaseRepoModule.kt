package com.example.data.di

import com.example.data.repositoryImpl.DatabaseRepositoryImpl
import com.sungil.database.MeetingManger.MeetingManger
import com.sungil.database.SharedPreference
import com.sungil.database.token.TokenManager
import com.sungil.database.user.UserData
import com.sungil.domain.repository.DatabaseRepository
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