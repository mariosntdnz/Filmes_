package com.example.filmes_.netWork

import androidx.paging.PagingSource
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.repository.FilmesRepository
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.flow

class PostDataSourceBD(private val bdService: FilmesRepository) : PagingSource<Int, FilmeModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmeModel> {
        try {

            val currentLoadingPageKey = params.key ?: 1
            val response = bdService.getAllFilmesFavoritados()?.map {
                ParseFilme.parseFilmeToModel(it)
            }
            val responseData = mutableListOf<FilmeModel>()

            response?.map {
                responseData.add(it!!)
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}