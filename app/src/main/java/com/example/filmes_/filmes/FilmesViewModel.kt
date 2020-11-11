 package com.example.filmes_.filmes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.PostDataSource
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.filmes_.netWork.repository.FilmesRepository
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception

class FilmesViewModel : ViewModel() {

    private val filmesRepository = FilmesRepository()

    private val _responseGeneros = MutableLiveData<ListaGeneros?>()
    val responseGeneros : LiveData<ListaGeneros?>
        get() = _responseGeneros

    private val _filmeClicado = MutableLiveData<Filme>()
    val filmeClicado : LiveData<Filme>
        get() = _filmeClicado

    private val _lastFilme = MutableLiveData<Filme>()
    val lastFilme : LiveData<Filme>
        get() = _lastFilme

    var dataFilmes = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(filmesRepository)
    }.flow.cachedIn(viewModelScope)

    init {
        getAllGeneros()
    }

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

    fun updateFavorite(filmeModel : FilmeModel){
        viewModelScope.launch {
            ParseFilme.parseModelToFilme(filmeModel)?.let {
                filmesRepository.insertFilmeFavoritado(
                    it
                )
            }
        }

        filmeModel.favorite.postValue(!filmeModel.favorite.value!!)

    }

    fun setFilmeClicado(filme : Filme){
        _filmeClicado.value = filme
    }

    fun nagationTelaDetalhes() {
        _lastFilme.value = _filmeClicado.value
        _filmeClicado.value = null
    }

}

