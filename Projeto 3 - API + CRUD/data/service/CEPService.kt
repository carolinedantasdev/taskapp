package com.example.apicontatos.data.service

import com.example.apicontatos.data.model.CEP
import retrofit2.http.GET
import retrofit2.http.Path

interface CEPService {
    @GET("ws/{cep}/json/")
    suspend fun getEndereco(@Path("cep") cep: String): CEP
}