package com.example.prueba.interfaces

import com.example.prueba.Pescado
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PescadoAPI {
    @GET("pescado")
    @Headers("Accept: application/json")
    fun obetenerPescados() : Call<List<Pescado>>

    @POST("pescado")
    fun createPescado(@Body pescado: Pescado) : Call<Pescado>
}