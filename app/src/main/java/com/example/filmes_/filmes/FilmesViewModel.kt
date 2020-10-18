package com.example.filmes_.filmes

import android.service.autofill.Validators.not
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes_.domain.FilmeModel
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

    private val _listaFilmes = MutableLiveData<List<FilmeModel?>?>()
    val listaFilmes : LiveData<List<FilmeModel?>?>
        get() = _listaFilmes

    private var _page : Int = 1

    init {
        getAllFilmes(_page)
        getAllGeneros()
    }

    private fun getAllFilmes(page : Int){
        viewModelScope.launch {
            try {
                _responseAllFilmes.value = filmesRepository.getAllMovies(page)
                _listaFilmes.value = _responseAllFilmes?.value?.results?.map { ParseFilme.parseFilmeToModel(it!!) }
            }
            catch (e : Exception){
                _responseAllFilmes.value = null
            }
        }
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

    fun insertFavorite(filmeModel : FilmeModel){
        _listaFilmes.value?.map {
            if(filmeModel.id == it?.id){
                it.favorite.postValue(!it.favorite.value!!)
            }
        }
    }

    fun setFilmeClicado(filme : Filme){
        _filmeClicado.value = filme
    }

    fun nagationTelaDetalhes(){
        _filmeClicado.value = null
    }

    fun setPage(page : Int){
        _page = page
    }
}

