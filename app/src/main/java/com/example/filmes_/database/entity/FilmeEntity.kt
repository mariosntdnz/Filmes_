package com.example.filmes_.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filme")
data class FilmeEntity(
    @PrimaryKey
    @ColumnInfo(name = "filme_id")
    val id : Int,
    val poster_path : String? = "",
    val title : String? = "",
    val overview : String? = "",
    var favorite : Boolean? = false
)