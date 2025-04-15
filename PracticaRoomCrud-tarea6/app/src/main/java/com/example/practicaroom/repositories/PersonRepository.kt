package com.example.practicaroom.repositories

import android.content.Context
import com.example.practicaroom.db.models.Pet

object PersonRepository {
    private suspend fun insertPet(context: Context, pet: Pet): Long {
        return RoomRepository
            .getRoomInstance(context)
            .petDao()
            .insertPet(pet)
    }

    suspend fun getPetById(context: Context, id: Int): Pet {
        return RoomRepository
            .getRoomInstance(context)
            .petDao()
            .getById(id)
    }

    suspend fun getPersonList(context: Context): List<Pet> {
        return RoomRepository
            .getRoomInstance(context)
            .petDao()
            .getAll()
    }

    suspend fun deletePerson(context: Context, person: Pet) {
        RoomRepository
            .getRoomInstance(context)
            .petDao()
            .deletePet(person)
    }

    suspend fun savePerson(context: Context, person: Pet): Int {
        if (person.id == 0) {
            return insertPet(context, person).toInt()
        } else {
            updatePerson(context, person)
            return person.id
        }
    }

    private suspend fun updatePerson(context: Context, person: Pet) {
        return RoomRepository
            .getRoomInstance(context)
            .petDao()
            .updatePet(person)
    }
}