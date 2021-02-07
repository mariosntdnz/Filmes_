package com.example.filmes_.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.Filme
import kotlinx.coroutines.flow.flow

class ParseFilme() {

    companion object{

        fun parseFilmeToModel(filme: Filme?) = filme?.let {
            FilmeModel(
                it.id,
                filme.poster_path,
                filme.genre_ids,
                filme.title,
                filme.overview,
                MutableLiveData(filme.favorite)
            )
        }

        fun parseModelToFilme(filmeModel: FilmeModel?) = filmeModel?.let {
            Filme(
            filmeModel.id,
            filmeModel.poster_path,
            filmeModel.genre_ids,
            filmeModel.title,
            filmeModel.overview,
            filmeModel.favorite.value
            )
        }

        fun parseEntityToModel(filmeEntity: FilmeEntity?) = filmeEntity?.let {
            FilmeModel(
                filmeEntity.id,
                filmeEntity.poster_path,
                null,
                filmeEntity.title,
                filmeEntity.overview,
                MutableLiveData(filmeEntity.favorite)
            )
        }
    }
}