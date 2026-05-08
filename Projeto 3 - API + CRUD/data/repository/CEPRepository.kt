package com.example.apicontatos.data.repository

import com.example.apicontatos.data.model.CEP
import com.example.apicontatos.data.remote.RetrofitClient
import retrofit2.http.Path

class CEPRepository {
    suspend fun getEndereco(cep: String): CEP {
        val cepDigits = cep.filter { it.isDigit() }
        require(cepDigits.length == 8) { "CEP deve conter 8 dígitos" }
        return RetrofitClient.cepApi.getEndereco(cep)
    }
}