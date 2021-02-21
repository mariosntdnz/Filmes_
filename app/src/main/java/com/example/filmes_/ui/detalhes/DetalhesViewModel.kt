package com.example.filmes_.ui.detalhes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.filmes_.netWork.repository.FilmesRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

class DetalhesViewModel : ViewModel() {
    private val filmesRepository = FilmesRepository()

    private val _responseGeneros = MutableLiveData<ListaGeneros?>()
    val responseGeneros : LiveData<ListaGeneros?>
        get() = _responseGeneros

    val hashMapGeneros = HashMap<Int?,String?>()

    init {
        getAllGeneros()
    }

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
    fun generateMapGeneros(){
        _responseGeneros.value?.generos?.forEach {
            it?.let {
                hashMapGeneros[it.id] = it.name
            }
        }
    }

    fun getGeneros(ids : List<Int>): MutableList<String> {
        val generos = ArrayList<String>()
        ids.forEach { i ->
            generos.add(hashMapGeneros[i].toString())
        }
        return generos
    }

    fun getGenerosFormatados(filmeModel: FilmeModel) = getGeneros(filmeModel.genre_ids?.filterNotNull()!!).joinToString(" | ")

    private fun getAllGeneros(){
        viewModelScope.launch {
            try {
                _responseGeneros.value = filmesRepository.getAllGenres()
            }
            catch (e : Exception){
                _responseGeneros.value = null
            }
        }
    }

    private fun updateFavoriteFilme(filmeModel: FilmeModel) {
        filmeModel.favorite.value = !filmeModel.favorite.value!!
    }
}