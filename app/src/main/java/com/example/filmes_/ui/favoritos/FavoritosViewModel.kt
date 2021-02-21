package com.example.filmes_.ui.favoritos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.repository.FilmesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritosViewModel : ViewModel(){

    private val filmesRepository = FilmesRepository()

    private val _filmeClicado = MutableLiveData<Filme?>()
    val filmeClicado : MutableLiveData<Filme?>
        get() = _filmeClicado

    private val _lastFilme = MutableLiveData<Filme>()
    val lastFilme : LiveData<Filme>
        get() = _lastFilme

    var dataListFilmes : Flow<List<FilmeModel?>>

    init {
        //dataLisFilmes = filmesRepository.getAllFilmesFavoritados()
        dataListFilmes = filmesRepository.getFilmeWithGenero()
    }

    fun updateFavorite(filmeModel : FilmeModel){
        updateFavoriteBD(filmeModel)
        updateFavoriteFilme(filmeModel)
    }

    private fun insertFilmeFavoritado(filme : FilmeModel) = viewModelScope.launch { filmesRepository.insertFilmeFavoritado(filme) }

    private fun deleteFilmeFavoritado(filme : FilmeModel) = viewModelScope.launch { filmesRepository.deleteFilmeFavoritado(filme) }

    fun updateFavoriteBD(filmeModel : FilmeModel){

        if(filmesRepository.searchFilme(filmeModel.id) == null) insertFilmeFavoritado(filmeModel)
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