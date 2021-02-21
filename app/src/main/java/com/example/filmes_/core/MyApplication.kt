package com.example.filmes_.core

import android.app.Application
import androidx.room.Room
import com.example.hackatonbndes.database.AppDatabase

open class MyApplication : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        //Room
        database = Room.databaseBuilder(this, AppDatabase::class.java, "my-db-filme").allowMainThreadQueries().build()
    }
}