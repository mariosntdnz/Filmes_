package com.example.filmes_.ui.detalhes

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.filmes_.netWork.repository.FilmesRepository
import com.example.filmes_.util.Parse
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

class DetalhesViewModel(application: Application) : ViewModel() {
    private val filmesRepository = FilmesRepository()

    private val _responseGeneros = MutableLiveData<ListaGeneros?>()
    val responseGeneros : LiveData<ListaGeneros?>
        get() = _responseGeneros

    init {
        val prefs = application.getSharedPreferences("com.example.filmes_", Context.MODE_PRIVATE)

        if(!prefs.getBoolean("genrerIsStored",false)){
            prefs.edit().putBoolean("genrerIsStored",true).apply()
            viewModelScope.launch {
                filmesRepository.insertGenresBD()
            }
        }
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

    fun getGeneros(ids : List<Int>): MutableList<String> {
        val generos = ArrayList<String>()
        val generosBd = Parse.parseGenreEntityListToGenero(filmesRepository.getGenresBD(ids))
        generosBd.map {
            it.name?.let { it1 -> generos.add(it1) }
        }
        return generos
    }

    fun getGenerosFormatados(filmeModel: FilmeModel) = getGeneros(filmeModel.genre_ids?.filterNotNull()!!).joinToString(" | ")

    private fun updateFavoriteFilme(filmeModel: FilmeModel) {
        filmeModel.favorite.value = !filmeModel.favorite.value!!
    }
}