package com.example.apicontatos.data.model

data class Contato(
    val id: Int? = null,
    val nome: String,
    val email: String,
    val telefone: String,
    val nascimento: String,
    val cep: String,
    val bairro: String,
    val logradouro: String,
    val numero: String,
    val cidade: String,
    val estado: String
)
