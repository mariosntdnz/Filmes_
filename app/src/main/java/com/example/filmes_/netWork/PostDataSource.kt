package com.example.filmes_.netWork

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.repository.FilmesRepository
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

class PostDataSource(private val apiService: FilmesRepository) : PagingSource<Int, FilmeModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmeModel> {
        try {

            val currentLoadingPageKey = params.key ?: 1
            val responseFavoritados = apiService.getAllFilmesFavoritadosList()
            val response = apiService.getAllMovies(currentLoadingPageKey).results?.map {filme->
               ParseFilme.parseFilmeToModel(filme)
            }

            val responseData = mutableListOf<FilmeModel>()

            response?.map {filme->

                val isfilmeFavoritado = responseFavoritados.find { it?.id == filme?.id }
                isfilmeFavoritado?.let {
                    filme?.favorite?.postValue(true)
                }
                responseData.add(filme!!)
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

    override fun getRefreshKey(state: PagingState<Int, FilmeModel>): Int? {
        TODO("Not yet implemented")
    }

}