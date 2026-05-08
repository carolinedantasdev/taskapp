package com.example.apicontatos.data.service

import com.example.apicontatos.data.model.Contato
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContatoService {
    @GET("contatos")
    suspend fun getContatos(): List<Contato>

    @GET("contatos/{id}")
    suspend fun getById(@Path("id") id: Int): Contato

    @POST("contatos")
    suspend fun addContato(@Body contato: Contato): Contato

    @DELETE("contatos/{id}")
    suspend fun removeContato(@Path("id") id: Int)

    @PUT("contatos/{id}")
    suspend fun updateContato(@Path("id") id: Int, @Body contato: Contato)

}