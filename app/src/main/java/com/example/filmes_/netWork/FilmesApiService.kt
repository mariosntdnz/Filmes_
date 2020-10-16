package com.example.filmes_.netWork

import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmesApiService {

    @GET("movie/popular?api_key=249d5cd45a52ee453a8cd04aa47dc448")
    suspend fun getAllMovies(@Query("page") type : Int?) : ListaFilmes

    @GET("genre/movie/list?api_key=249d5cd45a52ee453a8cd04aa47dc448")
    suspend fun getAllGenres() : ListaGeneros
}