package com.example.practicanavegacion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicanavegacion.data.local.model.AuthState

@Database(
    entities = [AuthState::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
}