package com.example.filmes_.domain

import androidx.lifecycle.MutableLiveData

data class FilmeModel(
    val id : Int,
    val poster_path : String?,
    var genre_ids : List<Int?>?,
    val title : String?,
    val overview : String?,
    var favorite : MutableLiveData<Boolean?> = MutableLiveData(false)
)