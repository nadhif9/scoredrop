package com.scoredrop.scoredrop.data

import kotlinx.serialization.Serializable

@Serializable
data class DataUser(
    val nama : String = "null", val nim : String = "null", val kelompok : String = "null", val presensi:String = "", val tp : String = "null", val praktikum : String = "null", val la : String = "null", val tugas : String = "null", val total : String = "null", val result : String = "null"
)
