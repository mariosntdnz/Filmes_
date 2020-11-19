package com.example.filmes_.netWork.repository

import com.example.filmes_.core.MyApplication
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.FilmesRestService
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.hackatonbndes.database.AppDatabase
import retrofit2.http.Query
import java.lang.Exception

class FilmesRepository {

    private val api = FilmesRestService
    private val bd = MyApplication.database?.filmeDao()!!

    suspend fun getAllGenres(): ListaGeneros {
        try {
            val response = api.getRetrofit().getAllGenres()
            if (response.generos != null) return response
            else throw Exception("Erro ao fazer a requisição")
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    suspend fun getAllMovies(@Query("page") type: Int?): ListaFilmes {
        try {
            val response = api.getRetrofit().getAllMovies(type)
            if (response.results != null) return response
            else throw Exception("Erro ao fazer a requisição")
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun searchFilme(id: Int): Filme? {
        var filmeEntity = bd.searchFilme(id)
        return filmeEntity?.let {
            Filme(it.id, it.poster_path, null, it.title, it.overview, it.favorite)
        }
    }

    fun insertFilmeFavoritado(filme: FilmeModel) {
        bd.insertFilmeFavoritado(
            FilmeEntity(
                filme.id,
                filme.poster_path,
                filme.title,
                filme.overview,
                filme.favorite.value
            )
        )
    }
    fun deleteFilmeFavoritado(filme: FilmeModel) {
        bd.deleteFilmeFavoritado(
            FilmeEntity(
                filme.id,
                filme.poster_path,
                filme.title,
                filme.overview,
                filme.favorite.value
            )
        )
    }
}