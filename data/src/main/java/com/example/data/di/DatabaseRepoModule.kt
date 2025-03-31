package com.example.data.di

import com.example.data.repositoryImpl.DatabaseRepositoryImpl
import com.sungil.database.SharedPreference
import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.dao.UserInfoDao
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
    fun provideDatabaseRepo(sharedPreference: SharedPreference , userInfo : UserInfoDao , tokenDao : TokenDao) : DatabaseRepository{
        return DatabaseRepositoryImpl(sharedPreference , userInfo,tokenDao)
    }
}