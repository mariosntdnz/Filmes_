package com.example.filmes_.ui.detalhes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.repository.FilmesRepository
import kotlinx.coroutines.launch

class DetalhesViewModel : ViewModel() {
    private val filmesRepository = FilmesRepository()

    fun updateFavorite(filmeModel : FilmeModel){
        updateFavoriteFilme(filmeModel)
        updateFavoriteBD(filmeModel)
    }

    private fun insertFilmeFavoritado(filme : FilmeModel) = viewModelScope.launch { filmesRepository.insertFilmeFavoritado(filme) }

    private fun deleteFilmeFavoritado(filme : FilmeModel) = viewModelScope.launch { filmesRepository.deleteFilmeFavoritado(filme) }

    fun updateFavoriteBD(filmeModel : FilmeModel){

        if(filmesRepository.searchFilme(filmeModel.id) == null) insertFilmeFavoritado(filmeModel)
        else deleteFilmeFavoritado(filmeModel)

    }

    private fun updateFavoriteFilme(filmeModel: FilmeModel) {
        filmeModel.favorite.value = !filmeModel.favorite.value!!
    }
}