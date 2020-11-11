package com.example.filmes_.netWork

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FilmesRestService {

    private val BASE_URL = "http://api.themoviedb.org/3/"

    private fun initConverterMoshi() : Moshi{
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    private fun initRetrofit() : Retrofit {

        var moshi = initConverterMoshi()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    fun getRetrofit() = initRetrofit().create(FilmesApiService::class.java)
}