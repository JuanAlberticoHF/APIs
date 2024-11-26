package com.juanalberticohf.apis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Autores::class], version = 3, exportSchema = false)

abstract class AutoresBBDD: RoomDatabase(){
    abstract fun autoresDAO(): AutoresDAO
        companion object DatabaseBuilder{
            private var INSTANCE : AutoresBBDD ? = null
            fun getInstance (context: Context): AutoresBBDD {
                if (INSTANCE == null) synchronized(Autores::class) {
                    INSTANCE = buildRoomDB(context)
                }
                return INSTANCE!!
            }
            private fun buildRoomDB (contexto : Context) =
                Room.databaseBuilder (
                    contexto.applicationContext, AutoresBBDD::class.java, "autores.db"
                ).fallbackToDestructiveMigration().build ()
        }
    }