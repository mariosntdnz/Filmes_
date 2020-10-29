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
import kotlinx.coroutines.launch
import java.lang.Exception

class FilmesViewModel : ViewModel() {

    private val filmesRepository = FilmesRepository()

    private val _responseAllFilmes = MutableLiveData<ListaFilmes?>()
    val responseAllFilmes : LiveData<ListaFilmes?>
        get() = _responseAllFilmes

    private val _responseGeneros = MutableLiveData<ListaGeneros?>()
    val responseGeneros : LiveData<ListaGeneros?>
        get() = _responseGeneros

    private val _filmeClicado = MutableLiveData<Filme>()
    val filmeClicado : LiveData<Filme>
        get() = _filmeClicado

    private val _lastFilme = MutableLiveData<Filme>()
    val lastFilme : LiveData<Filme>
        get() = _lastFilme

    private val _listaFilmes = MutableLiveData<List<FilmeModel?>?>()
    val listaFilmes : LiveData<List<FilmeModel?>?>
        get() = _listaFilmes

    val dataFilmes = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(filmesRepository)
    }.flow.cachedIn(viewModelScope)


    init {
        getAllFilmes()
        getAllGeneros()
    }

    private fun getAllFilmes(){

        /*viewModelScope.launch {
            try {
                _responseAllFilmes.value = filmesRepository.getAllMovies()
                _listaFilmes.value = _responseAllFilmes?.value?.results?.map { ParseFilme.parseFilmeToModel(it!!) }
            }
            catch (e : Exception){
                _responseAllFilmes.value = null
            }
        }*/
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
        var index = _listaFilmes.value?.indexOfFirst { it?.id == filmeModel.id }
        _listaFilmes.value?.get(index!!)?.favorite?.value = !_listaFilmes.value?.get(index!!)?.favorite?.value!!
    }

    fun setFilmeClicado(filme : Filme){
        _filmeClicado.value = filme
    }

    fun nagationTelaDetalhes() {
        _lastFilme.value = _filmeClicado.value
        _filmeClicado.value = null
    }

    fun attListFilmeVoltaDetalhes(){
        var filmeModel = ParseFilme.parseFilmeToModel(_lastFilme.value!!)
        var index = _listaFilmes.value?.indexOfFirst { it?.id == filmeModel.id }
        _listaFilmes.value?.get(index!!)?.favorite?.value = filmeModel.favorite.value
    }

}

