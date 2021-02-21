package com.example.filmes_.database.dao

import androidx.paging.PagingData
import androidx.room.*
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.database.entity.FilmeWithGenero
import com.example.filmes_.database.entity.GeneroIDEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilmeFavoritado(filme : FilmeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeneroIDList(generoIDEntity: List<GeneroIDEntity?>?)

    @Delete
    fun deleteFilmeFavoritado(filme : FilmeEntity)

    @Query("SELECT * FROM filme WHERE filme_id = :filmeId ")
    fun searchFilme(filmeId : Int) : FilmeEntity?

    @Query("SELECT * FROM filme")
    fun getAllFilmesFavoritados() : Flow<List<FilmeEntity?>>

    @Transaction
    @Query("SELECT * FROM filme")
    fun getFilmeWithGenero(): Flow<List<FilmeWithGenero>>
}