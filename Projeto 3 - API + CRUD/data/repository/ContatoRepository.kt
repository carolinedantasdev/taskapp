package com.example.apicontatos.data.repository

import com.example.apicontatos.data.model.Contato
import com.example.apicontatos.data.remote.RetrofitClient


class ContatoRepository {
    suspend fun getContatos(): List<Contato> {
        return RetrofitClient.api.getContatos()
    }

    suspend fun addContato(contato: Contato): Contato {
        return RetrofitClient.api.addContato(contato)
    }

    suspend fun getById(id: Int): Contato {
        return RetrofitClient.api.getById(id)
    }

    suspend fun removeContato(id: Int) {
        RetrofitClient.api.removeContato(id)
    }

    suspend fun updateContato(id: Int, contato: Contato) {
        RetrofitClient.api.updateContato(id, contato)
    }
}