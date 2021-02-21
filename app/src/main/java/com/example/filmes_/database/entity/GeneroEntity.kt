package com.example.filmes_.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
@Entity(tableName = "genero")
data class GeneroEntity(
    @PrimaryKey
    val id : Int?,
    val name : String?
)