package com.example.apicontatos.data.remote

import com.example.apicontatos.data.service.CEPService
import com.example.apicontatos.data.service.ContatoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "https://api-contatos-hozs.onrender.com/"
    private val BASE_CEP_URL = "https://viacep.com.br/"

    val api: ContatoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<ContatoService>(ContatoService::class.java)
    }

    val cepApi: CEPService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_CEP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<CEPService>(CEPService::class.java)
    }
}