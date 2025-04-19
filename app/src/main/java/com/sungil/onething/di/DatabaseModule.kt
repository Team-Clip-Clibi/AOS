package com.sungil.onething.di

import android.content.Context
import androidx.room.Room
import com.sungil.database.SharedPreference
import com.sungil.database.SharedPreferenceImpl
import com.sungil.database.room.AppDatabase
import com.sungil.database.room.dao.MeetingDao
import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.room.model.Token
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