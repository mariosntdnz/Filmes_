package com.example.filmes_.netWork.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filme(
    val id : Int,
    val poster_path : String?,
    val genre_ids : List<Int?>?,
    val title : String?,
    val overview : String?,
    var favorite : Boolean? = false
) : Parcelable