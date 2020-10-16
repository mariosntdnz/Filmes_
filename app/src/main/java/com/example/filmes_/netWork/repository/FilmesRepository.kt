package com.example.filmes_.netWork.repository

import com.example.filmes_.netWork.FilmesRestService
import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import retrofit2.Response
import retrofit2.http.Query
import java.lang.Exception

class FilmesRepository {

    private val api = FilmesRestService

    suspend fun getAllGenres() : ListaGeneros {
        try {
            val response = api.getRetrofit().getAllGenres()

            if(response.generos != null) return response
            else throw Exception("Erro ao fazer a requisição")
        }
        catch (e: Exception){

            throw Exception(e.message)

        }
    }
    suspend fun getAllMovies(@Query("page") type: Int?) : ListaFilmes{
        try {

            val response = api.getRetrofit().getAllMovies(type)
            println("PORRa")
            if(response.results != null) return response
            else throw Exception("Erro ao fazer a requisição")
        }
        catch (e: Exception){

            println(e.message)
            throw Exception(e.message)

        }
    }
}