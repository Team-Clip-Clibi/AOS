package com.clip.onething.di

import android.content.Context
import androidx.room.Room
import com.clip.database.SharedPreference
import com.clip.database.SharedPreferenceImpl
import com.clip.database.room.AppDatabase
import com.clip.database.room.dao.MeetingDao
import com.clip.database.room.dao.TokenDao
import com.clip.database.room.dao.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideSharedPreference(@ApplicationContext context : Context) : SharedPreference{
        return SharedPreferenceImpl(context)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserInfoDao(appDatabase: AppDatabase): UserInfoDao {
        return appDatabase.userInfoDao()
    }

    @Provides
    fun provideTokenDao(appDatabase: AppDatabase): TokenDao {
        return appDatabase.tokenDao()
    }

    @Provides
    fun provideMeetingDao(appDatabase: AppDatabase) : MeetingDao {
        return appDatabase.meetingDao()
    }
}