package com.example.practicanavegacion.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicanavegacion.data.local.model.AuthState

@Dao
interface AuthDao {
    @Query("SELECT * FROM auth_state LIMIT 1")
    suspend fun getAuthState(): AuthState?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthState(state: AuthState)

    @Query("DELETE FROM auth_state")
    suspend fun clearAuthState()
}