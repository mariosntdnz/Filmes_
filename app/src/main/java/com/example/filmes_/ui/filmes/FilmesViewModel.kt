package com.example.filmes_.ui.filmes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.PostDataSource
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.repository.FilmesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FilmesViewModel : ViewModel() {

    private val filmesRepository = FilmesRepository()

    private val _filmeClicado = MutableLiveData<Filme?>()
    val filmeClicado : MutableLiveData<Filme?>
        get() = _filmeClicado

    private val _lastFilme = MutableLiveData<Filme>()

    var dataFilmes : Flow<PagingData<FilmeModel>>

    init {

        dataFilmes = Pager(PagingConfig(pageSize = 6)) {
            PostDataSource(filmesRepository)
        }.flow.cachedIn(viewModelScope)

    }

    fun updateData(){
        dataFilmes = Pager(PagingConfig(pageSize = 6)) {
            PostDataSource(filmesRepository)
        }.flow.cachedIn(viewModelScope)
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

    private fun updateFavoriteFilme(filmeModel: FilmeModel) {
        filmeModel.favorite.value = !filmeModel.favorite.value!!
    }

    fun setFilmeClicado(filme : Filme){
        _filmeClicado.value = filme
    }

    fun nagationTelaDetalhes() {
        _lastFilme.value = _filmeClicado.value
        _filmeClicado.value = null
    }

}

