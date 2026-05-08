package com.example.apicontatos.data.model

data class CEP(
    val cep: String? = null,
    val logradouro: String? = null,
    val bairro: String? = null,
    val localidade: String? = null,
    val estado: String? = null,
    val erro: Boolean? = null
)
