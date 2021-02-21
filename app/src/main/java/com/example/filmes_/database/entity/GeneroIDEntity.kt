package com.example.filmes_.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class GeneroIDEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    val filmeId : Int = 0,
    val generoId : Int = 0
)