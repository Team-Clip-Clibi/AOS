package com.oneThing.onething.di


import com.oneThing.database.room.dao.UserInfoDao
import com.oneThing.database.user.UserData
import com.oneThing.database.user.UserDataImpl
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