package com.example.filmes_.netWork

import androidx.paging.PagingSource
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.repository.FilmesRepository

class PostDataSource(private val apiService: FilmesRepository) : PagingSource<Int, Filme>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Filme> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getAllMovies(currentLoadingPageKey).results
            val responseData = mutableListOf<Filme>()
            response?.map {
                responseData.add(it!!)
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}