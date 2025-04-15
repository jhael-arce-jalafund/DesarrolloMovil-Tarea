package com.example.practicanavegacion.di

import android.content.Context
import androidx.room.Room
import com.example.practicanavegacion.data.local.AppDatabase
import com.example.practicanavegacion.data.local.AuthDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "auth_db"
        ).build()
    }

    @Provides
    fun provideAuthDao(database: AppDatabase): AuthDao = database.authDao()
}