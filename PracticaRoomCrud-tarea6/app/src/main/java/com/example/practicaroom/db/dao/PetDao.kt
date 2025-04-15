package com.example.practicaroom.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.practicaroom.db.models.Pet

@Dao
interface PetDao {
    @Query("SELECT * FROM Pet")
    suspend fun getAll(): List<Pet>

    @Query("SELECT * FROM Pet WHERE id = :id")
    suspend fun getById(id: Int): Pet

    @Query("SELECT * FROM Pet WHERE personId = :personId")
    suspend fun getByPersonId(personId: Int): List<Pet>

    @Insert
    suspend fun insertPet(pet: Pet): Long

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)
}