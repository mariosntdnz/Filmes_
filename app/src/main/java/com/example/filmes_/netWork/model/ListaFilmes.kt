package com.example.filmes_.netWork.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListaFilmes(
    val page : Int?,
    val results : List<Filme?>?
) : Parcelable