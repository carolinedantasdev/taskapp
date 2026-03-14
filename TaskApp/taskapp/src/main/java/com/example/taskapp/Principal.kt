package com.example.taskapp

import java.util.Scanner

/*
Projeto - Introdução ao Desenvolvimento com Kotlin

Criar um projeto contendo as seguintes características:

1. Deverá ter uma classe de modelo;

2. Crie uma estrutura de lista para gerenciar os objetos oriundos da classe;

3. Deverá ter as seguintes funcionalidades:
A) Cadastrar;
B) Listar;
C) Pesquisar;
D) Alterar;
E) Remover;
F) Finalizar.

4. Deverá ter o uso de pelo menos um Null Safety;

5. Implemente pelo menos um Elvis Operator;

6. Estruture utilizando os conceitos de POO;

7. Realize validações para garantir registros coesos;

8. Para criar essa interação com o usuário, utilize a classe Scanner. - ok
*/

enum class Status(val nivel: Int) {
    ABERTA(1), EM_ANDAMENTO(2), CONCLUIDA(3);

    companion object {
        fun fromInput(input: Int?): Status? {
            return when (input) {
                1 -> ABERTA
                2 -> EM_ANDAMENTO
                3 -> CONCLUIDA
                else -> null
            }
        }
    }
}

class Task {
    var id: Int = 1
    var titulo: String? = null
    var descricao: String? = null
    var status: Status

    constructor(id: Int, titulo: String, descricao: String) {
        this.id = id
        this.titulo = titulo
        this.descricao = descricao
        this.status = Status.ABERTA
    }
}

abstract class Crud {
    abstract fun cadastrar()
    abstract fun listar()
    abstract fun pesquisar()
    abstract fun alterarStatus()
    abstract fun remover()
    abstract fun finalizar()
}

class AcoesTaks(): Crud() {
    private var listaTask = mutableListOf<Task>()
    private val scanner = Scanner(System.`in`)
    private var nextId: Int = 1

    override fun cadastrar() {
        print("Título: ")
        val titulo = scanner.nextLine()
        print("Descricao: ")
        val descricao = scanner.nextLine()

        var task = Task(nextId++, titulo, descricao)
        listaTask.add(task)
        println("Cadastrado com sucesso!")
    }

    override fun listar() {
        if (listaTask.isEmpty()) println("Nenhum resultado encontrado.")
        listaTask.forEach {
            imprimir(it)
        }
    }

    override fun pesquisar() {
        println("Selecione a opção do status ao qual deseja pesquisar: ")
        println("1 - ABERTA")
        println("2 - EM ANDAMENTO")
        println("3 - CONCLUIDA")

        val status = scanner.nextInt()
        if (status == 0 || status > 3) {
            println("Opção inválida.")
            return
        }

        val resultado = listaTask.filter { it.status.ordinal == status }
        if (resultado.isEmpty()) {
            println("Nenhum resultado encontrado")
            return
        }
        println("Tasks encontradas: ")
        resultado.forEach { imprimir(it) }

    }

    override fun alterarStatus() {
        println("Id da task que deseja alterar: ")
        val id = scanner.nextInt()
        val task =  pesquisarPorId(id)
        if (task == null) {
            println("Id não encontrado.")
            return
        }

        println("Selecione um novo status: ")
        println("1 - ABERTA")
        println("2 - EM ANDAMENTO")
        println("3 - CONCLUIDA")
        val status = scanner.nextInt()
        if (status == 0 || status > 3) {
            println("Opção inválida.")
            return
        }
        var novoStatus = Status.fromInput(status) ?: return

        task.status = novoStatus
        println("Alterada com sucesso! ")
    }

    override fun remover() {
        println("Id da task que deseja remover: ")
        val id = scanner.nextInt()
        val task =  pesquisarPorId(id)
        if (task == null) {
            println("Id não encontrado.")
            return
        }
        listaTask.remove(task)
    }

    override fun finalizar() {
        println("Finalizando... Até mais!")
        return
    }

    fun imprimir(task: Task): Unit {
        println("Id: ${ task.id}")
        println("Titulo: ${ task.titulo}")
        println("Descricao: ${ task.descricao}")
        println("Status: ${ task.status}")
        println("=====================")
    }

    fun pesquisarPorId(id: Int): Task? = listaTask.find { it.id == id }
}


fun main() {
    val acoes = AcoesTaks()
    var opcao : Int = 0
    var obj = Scanner(System.`in`)

    do {
        println("1 - Cadastrar")
        println("2 - Listar")
        println("3 - Pesquisar por status")
        println("4 - Alterar status")
        println("5 - Remover")
        println("6 - Finalizar")

        println("Escolha uma opção: ")
        opcao = obj.nextInt()

        val opcaoSelecionada = when (opcao) {
            1 -> acoes.cadastrar()
            2 -> acoes.listar()
            3 -> acoes.pesquisar()
            4 -> acoes.alterarStatus()
            5 -> acoes.remover()
            6 -> acoes.finalizar()
            else -> println("Opção inválida.")
        }

    } while (opcao != 6)

}