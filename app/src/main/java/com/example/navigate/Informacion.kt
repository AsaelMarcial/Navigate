package com.example.navigate;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Informacion(
    val id: Int,
    val latitud: String,
    val longitud: String,
    val presion: String,
    val humedad: String,
    val temperatura: String,
    val coordenadas: String,
    val giroscopio: String,
    val fecha: String,
    val dt_created: String

)
