package com.example.practicabooksoffline.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicabooksoffline.db.dao.MovieDao
import com.example.practicabooksoffline.db.models.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private const val DATABASE_NAME = "movies.db"
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}