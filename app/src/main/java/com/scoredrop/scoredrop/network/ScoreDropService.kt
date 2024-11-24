package com.scoredrop.scoredrop.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.scoredrop.scoredrop.data.DataUser
import com.scoredrop.scoredrop.data.Team
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL ="http://192.168.1.3:5001"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ScoreDropService{
    @GET("scoredrop/mahasiswa/{nim}")
    suspend fun getNilai(@Path("nim") nim : String): List<DataUser>

    @GET("scoredrop/kelompok/{kelompok}")
    suspend fun getTeam(@Path("kelompok") team : String): List<Team>
}

object ScoreDropApi{
    val retrofitService : ScoreDropService by lazy{
    retrofit.create(ScoreDropService::class.java)
    }
}