package com.example.filmes_.netWork

import androidx.paging.PagingSource
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.repository.FilmesRepository
import com.example.filmes_.util.ParseFilme

class PostDataSource(private val apiService: FilmesRepository) : PagingSource<Int, FilmeModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmeModel> {
        try {

            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getAllMovies(currentLoadingPageKey).results?.map {
                ParseFilme.parseFilmeToModel(it)
            }

            val responseData = mutableListOf<FilmeModel>()

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