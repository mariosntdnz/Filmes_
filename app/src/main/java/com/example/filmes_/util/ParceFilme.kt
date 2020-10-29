package com.example.filmes_.util

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.Filme

class ParseFilme() {

    companion object{

        fun parseFilmeToModel(filme: Filme) = FilmeModel(
            filme.id,
            filme.poster_path,
            filme.genre_ids,
            filme.title,
            filme.overview,
            MutableLiveData<Boolean?>().apply { value = filme.favorite }
        )

        fun parseModelToFilme(filmeModel: FilmeModel) = Filme(
            filmeModel.id,
            filmeModel.poster_path,
            filmeModel.genre_ids,
            filmeModel.title,
            filmeModel.overview,
            filmeModel.favorite.value
        )
    }
}