package com.example.filmes_.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmes_.database.entity.GeneroEntity

@Dao
interface GeneroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeneros(generoEntity: List<GeneroEntity>)

    @Query("SELECT * FROM genero WHERE id IN (:listId)")
    fun getGeneros(listId : List<Int>) : List<GeneroEntity>
}