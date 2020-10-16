package com.example.filmes_.netWork.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListaGeneros(
    @Json(name = "genres") val generos : List<Genero?>?
):Parcelable