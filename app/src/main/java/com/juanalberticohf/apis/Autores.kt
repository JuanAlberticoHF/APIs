package com.juanalberticohf.apis

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Autores (
    @PrimaryKey var key: String,
    @ColumnInfo var birth_date: String?,
    @ColumnInfo var name: String){
}