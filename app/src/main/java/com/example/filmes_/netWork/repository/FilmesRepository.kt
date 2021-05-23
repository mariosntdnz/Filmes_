package com.example.filmes_.netWork.repository

import com.example.filmes_.core.MyApplication
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.database.entity.GeneroIDEntity
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.FilmesRestService
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.model.Genero
import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.filmes_.util.Parse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import retrofit2.http.Query

class FilmesRepository{

    private val api = FilmesRestService
    private val bdFilme = MyApplication.database?.filmeDao()!!
    private val bdGenero = MyApplication.database?.generoDao()!!


    suspend fun getAllGenres(): ListaGeneros {
        try {
            println("REQ")
            val response = api.getRetrofit().getAllGenres()
            if (response.generos != null) return response
            else throw Exception("Erro ao fazer a requisição")
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    suspend fun insertGenresBD(){
        Parse.parseGenreListToEntity(getAllGenres().generos)?.let { bdGenero.insertGeneros(it) }
    }

    fun getGenresBD(listId : List<Int>) = bdGenero.getGeneros(listId)

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
        var filmeEntity = bdFilme.searchFilme(id)
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

        bdFilme.insertFilmeFavoritado(
            FilmeEntity(
                filme.id,
                filme.poster_path,
                filme.title,
                filme.overview,
                filme.favorite.value
            )
        )

        getGeneroIDListFromFilme(filme)?.let {

            bdFilme.insertGeneroIDList(it)
        }

    }

    fun deleteFilmeFavoritado(filme: FilmeModel) {
        bdFilme.deleteFilmeFavoritado(
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
        return bdFilme.getAllFilmesFavoritados().transform {
            this.emit(it.map{Parse.parseEntityToModel(it)})
        }
    }

    suspend fun getAllFilmesFavoritadosList() : List<FilmeModel?> {
        return bdFilme.getAllFilmesFavoritados().first().map { Parse.parseEntityToModel(it) }
    }

    fun getFilmeWithGenero(): Flow<List<FilmeModel?>>{
        return bdFilme.getFilmeWithGenero().transform { it ->
            this.emit(
                it.map {
                    val filme = Parse.parseEntityToModel(it.filmeEntity)
                    val genero = it.listGeneroIDsEntity
                    filme?.genre_ids = genero.map { it.generoId }
                    filme
                }
            )
        }
    }
}