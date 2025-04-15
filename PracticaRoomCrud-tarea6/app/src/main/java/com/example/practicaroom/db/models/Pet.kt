package com.example.practicaroom.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pet(
    var name: String,
    var type: String,
    var personId: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}