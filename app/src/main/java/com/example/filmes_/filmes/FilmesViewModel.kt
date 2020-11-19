package com.example.filmes_.filmes

import androidx.lifecycle.*
import androidx.paging.*
import com.example.filmes_.database.entity.FilmeEntity
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.PostDataSource
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.model.ListaFilmes
import com.example.filmes_.netWork.model.ListaGeneros
import com.example.filmes_.netWork.repository.FilmesRepository
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.*
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

    var dataFilmes : Flow<PagingData<FilmeModel>>

    init {

        dataFilmes = Pager(PagingConfig(pageSize = 6)) {
            PostDataSource(filmesRepository)
        }.flow.cachedIn(viewModelScope)

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
        updateFavoriteBD(filmeModel)
        updateFavoriteFilme(filmeModel)
    }

    private fun insertFilmeFavoritado(filme : FilmeModel) = viewModelScope.launch { filmesRepository.insertFilmeFavoritado(filme) }

    private fun deleteFilmeFavoritado(filme : FilmeModel) = viewModelScope.launch { filmesRepository.deleteFilmeFavoritado(filme) }

    private fun updateFavoriteBD(filmeModel : FilmeModel){

        if(filmeModel.favorite.value!!) insertFilmeFavoritado(filmeModel)
        else deleteFilmeFavoritado(filmeModel)

    }

    private fun updateFavoriteFilme(filmeModel: FilmeModel) {
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

