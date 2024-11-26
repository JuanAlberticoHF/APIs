package com.juanalberticohf.apis

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AutoresDAO {
    @Query("Select * from autores")
    fun selectAll():List<Autores>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(autor:Autores):Long

    @Delete
    fun delete (autor:Autores)
}