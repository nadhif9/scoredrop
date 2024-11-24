package com.scoredrop.scoredrop.data

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val nama:String = "", val nim:String = "", val presensi:String = "",val result:String = ""
)
