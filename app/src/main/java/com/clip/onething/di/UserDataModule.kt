package com.clip.onething.di


import com.clip.database.room.dao.UserInfoDao
import com.clip.database.user.UserData
import com.clip.database.user.UserDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Provides
    @Singleton
    fun provideUserData(userInfoDao: UserInfoDao): UserData {
        return UserDataImpl(userInfoDao)
    }
}