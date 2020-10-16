package com.example.filmes_.netWork.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genero(
    val id : Int?,
    val name : String?
):Parcelable