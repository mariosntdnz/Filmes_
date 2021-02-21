package com.example.filmes_.netWork.repository

import com.example.filmes_.core.MyApplication
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.database.entity.FilmeWithGenero
import com.example.filmes_.database.entity.GeneroIDEntity
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.FilmesRestService
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import retrofit2.http.Query

class FilmesRepository{

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

    private fun getGeneroIDListFromFilme(filme: FilmeModel) : List<GeneroIDEntity>?{
        return filme.genre_ids?.map {
            GeneroIDEntity(null,filme.id,it!!)
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

        getGeneroIDListFromFilme(filme)?.let {

            bd.insertGeneroIDList(it)
        }

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

    fun getAllFilmesFavoritados() : Flow<List<FilmeModel?>> {
        return bd.getAllFilmesFavoritados().transform {
            this.emit(it.map{ParseFilme.parseEntityToModel(it)})
        }
    }

    suspend fun getAllFilmesFavoritadosList() : List<FilmeModel?> {
        return bd.getAllFilmesFavoritados().first().map { ParseFilme.parseEntityToModel(it) }
    }

    fun getFilmeWithGenero(): Flow<List<FilmeModel?>>{
        return bd.getFilmeWithGenero().transform { it ->
            this.emit(
                it.map {
                    val filme = ParseFilme.parseEntityToModel(it.filmeEntity)
                    val genero = it.listGeneroIDsEntity
                    filme?.genre_ids = genero.map { it.generoId }
                    filme
                }
            )
        }
    }
}