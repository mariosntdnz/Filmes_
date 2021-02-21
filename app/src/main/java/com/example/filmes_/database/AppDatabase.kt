package com.example.hackatonbndes.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.filmes_.MainActivity
import com.example.filmes_.database.dao.FilmeDao
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.database.entity.FilmeWithGenero
import com.example.filmes_.database.entity.GeneroEntity
import com.example.filmes_.database.entity.GeneroIDEntity

@Database(
    entities = [
        FilmeEntity::class,GeneroIDEntity::class,GeneroEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun filmeDao() : FilmeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase (): AppDatabase? {
            if (this.INSTANCE != null) {
                return this.INSTANCE
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        MainActivity.instance,
                        AppDatabase::class.java,
                        "ha")
                        .build()
                    this.INSTANCE = instance
                    return instance
                }
            }
        }
    }
}