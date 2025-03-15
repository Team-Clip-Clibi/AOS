package com.example.data.di

import com.example.data.repositoryImpl.DatabaseRepositoryImpl
import com.sungil.database.SharedPreference
import com.sungil.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseRepoModule {
    @Provides
    fun provideDatabaseRepo(sharedPreference: SharedPreference) : DatabaseRepository{
        return DatabaseRepositoryImpl(sharedPreference)
    }
}