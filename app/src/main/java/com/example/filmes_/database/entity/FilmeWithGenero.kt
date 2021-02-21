package com.example.filmes_.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FilmeWithGenero(
    @Embedded val filmeEntity: FilmeEntity ,
    @Relation(
        parentColumn = "filme_id",
        entityColumn = "filmeId"
    )
    val listGeneroIDsEntity: List<GeneroIDEntity>
)