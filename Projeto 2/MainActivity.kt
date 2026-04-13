package com.example.navegacaoexerc2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navegacaoexerc2.ui.theme.NavegacaoExerc2Theme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoExerc2Theme {
                GerenciadorNavegacao()
            }
        }
    }
}

@Composable
fun GerenciadorNavegacao() {
    val navController = rememberNavController()

    var adicionarGanho by remember { mutableStateOf(0) }

    var listaItens = mutableListOf<ItemCard>()

    listaItens.add(ItemCard("Salário", 20000.00, Tipo.GANHO))
    listaItens.add(ItemCard("Freelance", 15000.00, Tipo.GANHO))
    listaItens.add(ItemCard("Conserto da máquina de lavar", 250.00, Tipo.GASTO))
    listaItens.add(ItemCard("Gasolina", 800.00, Tipo.GASTO))
    listaItens.add(ItemCard("Comprar casa", 250000.00, Tipo.SONHO))
    listaItens.add(ItemCard("Comprar carro", 150000.00, Tipo.SONHO))

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("inicio") },
                    icon = { Icon(Icons.Default.Home, null) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("ganhos") },
                    icon = { Icon(painterResource(id = R.drawable.ganhos), null, Modifier.size(24.dp)) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("gastos") },
                    icon = {
                        Icon( painterResource(id = R.drawable.gastos), null, Modifier.size(24.dp))
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("sonhos") },
                    icon = {
                        Icon(painterResource(id = R.drawable.sonhos), null, Modifier.size(24.dp))
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") {
                Inicio(listaItens)
            }

            composable("ganhos") {
                TelaCard(
                    "Ganho",
                    listaItens,
                    telaAdicionar = { navController.navigate("telaAdicionar/Ganho") }
                )
            }

            composable("gastos") {
                TelaCard(
                    "Gasto",
                    listaItens,
                    telaAdicionar = { navController.navigate("telaAdicionar/Gasto") }
                )
            }

            composable("sonhos") {
                TelaCard(
                    "Sonho",
                    listaItens,
                    telaAdicionar = { navController.navigate("telaAdicionar/Sonho") }
                )
            }

            composable("telaAdicionar/{parametro}") {
                backStackEntry -> val fluxo = backStackEntry.arguments?.getString("parametro").toString()
                AdicionarItem(
                    fluxo = fluxo,
                    navController = navController,
                    onAdicionarItem = { item ->
                        listaItens.add(item)
                    }
                )
            }
        }
    }
}

@Composable
fun Inicio(
    listaItens: List<ItemCard>
) {
    var saldo = 0.0
    listaItens.forEach {
        if (it.tipo.equals(Tipo.GANHO))
            saldo += it.valor
        else if (it.tipo.equals(Tipo.GASTO))
            saldo -= it.valor
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        val nf = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Olá, Carol",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Você é cliente Plus+",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Saldo em conta",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = nf.format(saldo))
                }
            }
        }
    }
}

@Composable
fun TelaCard(
    fluxo: String,
    lista: List<ItemCard>,
    telaAdicionar: () -> Unit
) {
    val nf = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "${fluxo}s",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            lista
                .filter { it.tipo == Tipo.fromFluxo(fluxo) }
                .forEach { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = item.descricao,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = nf.format(item.valor))
                        }
                    }
                }

            Spacer(Modifier.height(40.dp))
            Button(onClick = telaAdicionar) {
                Text("Adicionar novo ${fluxo.lowercase()}")
            }
        }
    }
}

@Composable
fun AdicionarItem(
    fluxo: String,
    navController: NavController,
    onAdicionarItem: (ItemCard) -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Adicionar novo $fluxo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            TextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Informe a descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Informe o valor") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val novoItem = ItemCard(
                        descricao = descricao,
                        valor = valor.toDoubleOrNull() ?: 0.0,
                        tipo = Tipo.fromFluxo(fluxo)
                    )

                    onAdicionarItem(novoItem)

                    navController.popBackStack()

                    descricao = ""
                    valor = ""
                }
            ) {
                Text("Cadastrar")
            }
        }
    }
}

class ItemCard {
    var descricao: String = ""
    var valor: Double = 0.0
    var tipo: Tipo = Tipo.INDEFINIDO

    constructor(descricao: String, valor: Double, tipo: Tipo) {
        this.descricao = descricao
        this.valor = valor
        this.tipo = tipo
    }
}

enum class Tipo {
    INDEFINIDO, GANHO, GASTO, SONHO;

    companion object {
        fun fromFluxo(fluxo: String?): Tipo {
            return when (fluxo?.uppercase()) {
                "GANHO" -> GANHO
                "GASTO" -> GASTO
                "SONHO" -> SONHO
                else -> INDEFINIDO
            }
        }
    }
}