package com.cirogg.conexa.di

import android.content.Context
import androidx.room.Room
import com.cirogg.conexa.data.model.news.NewsDao
import com.cirogg.conexa.data.model.news.NewsDatabase
import com.cirogg.conexa.data.model.users.UsersDao
import com.cirogg.conexa.data.model.users.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext appContext: Context): NewsDatabase {
        return Room.databaseBuilder(
            appContext,
            NewsDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    fun provideNewsDao(appDatabase: NewsDatabase): NewsDao {
        return appDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext appContext: Context): UsersDatabase {
        return Room.databaseBuilder(
            appContext,
            UsersDatabase::class.java,
            "users_database"
        ).build()
    }

    @Provides
    fun provideUserssDao(appDatabase: UsersDatabase): UsersDao {
        return appDatabase.usersDao()
    }
}