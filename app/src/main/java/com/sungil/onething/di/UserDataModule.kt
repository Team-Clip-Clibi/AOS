package com.sungil.onething.di


import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.user.UserData
import com.sungil.database.user.UserDataImpl
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