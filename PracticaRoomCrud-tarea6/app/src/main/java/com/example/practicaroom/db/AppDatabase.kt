package com.example.practicaroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicaroom.db.dao.PetDao
import com.example.practicaroom.db.models.Pet

@Database(entities = [Pet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}