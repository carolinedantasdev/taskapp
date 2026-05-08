package com.example.apicontatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apicontatos.data.model.Contato
import com.example.apicontatos.data.repository.CEPRepository
import com.example.apicontatos.data.repository.ContatoRepository
import com.example.apicontatos.ui.theme.ApiContatosTheme
import kotlinx.coroutines.launch
import kotlin.math.log
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

class MainActivity : ComponentActivity() {

    val contatoRepository = ContatoRepository()
    val cepRepository = CEPRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiContatosTheme {
                ContatoScreen(contatoRepository, cepRepository)
            }
        }
    }
}

@Composable
fun ContatoScreen(contatoRepository: ContatoRepository, cepRepository: CEPRepository) {

    var contatos by remember { mutableStateOf<List<Contato>>(emptyList()) }

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var nascimento by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var alterar by remember { mutableStateOf(false) }
    var idAlterando by remember { mutableStateOf<Int?>(null) }


    LaunchedEffect(Unit) {
        contatos = contatoRepository.getContatos()
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(20.dp)
    ) {
        // ********** FORMULÁRIO DE CADASTRO
        Column(
            modifier = Modifier
                .weight(0.6f)
                .verticalScroll(rememberScrollState())
        ) {
        Text(
            text = if (!alterar) "Cadastrar Contato" else "Alterar Contato",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        TextField(
            value = nome,
            onValueChange = { nome = it},
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it},
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = telefone,
            onValueChange = { telefone = it},
            label = { Text("Telefone") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = nascimento,
            onValueChange = { nascimento = it},
            label = { Text("Nascimento") },
            placeholder = { Text("00/00/0000") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = cep,
                onValueChange = { cep = it },
                label = { Text("CEP") },
                placeholder = { Text("00000-000") },
                modifier = Modifier.padding(bottom = 5.dp, end = 10.dp).weight(0.75f),
                maxLines = 1
            )

            Button(
                onClick = {
                    scope.launch {
                        try {
                            val resposta = cepRepository.getEndereco(cep)
                            if (resposta.erro == true) {
                                Toast.makeText(context, "CEP não encontrado", Toast.LENGTH_SHORT).show()
                            } else {
                                logradouro = resposta.logradouro ?: ""
                                bairro = resposta.bairro ?: ""
                                cidade = resposta.localidade ?: ""
                                estado = resposta.estado ?: ""
                            }
                        } catch (e: IllegalArgumentException) {
                            Toast.makeText(context, "CEP inválido. Digite 8 dígitos.", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erro ao consultar CEP: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) {
                Text("Consultar CEP")
            }
        }

        TextField(
            value = bairro,
            onValueChange = { bairro = it},
            label = { Text("Bairro") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = logradouro,
            onValueChange = { logradouro = it},
            label = { Text("Logradouro") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = numero,
            onValueChange = { numero = it},
            label = { Text("Número") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = estado,
            onValueChange = { estado = it},
            label = { Text("Estado") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        TextField(
            value = cidade,
            onValueChange = { cidade = it},
            label = { Text("Cidade") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
        )
        Button(
            onClick = {
                scope.launch {
                    val contato = Contato(
                        nome = nome,
                        email = email,
                        telefone = telefone,
                        nascimento = nascimento,
                        cep = cep,
                        bairro = bairro,
                        logradouro = logradouro,
                        numero = numero,
                        estado = estado,
                        cidade = cidade
                    )

                    if (alterar) {
                        idAlterando?.let { contatoRepository.updateContato(it, contato) }
                    } else {
                        contatoRepository.addContato(contato)
                    }

                    contatos = contatoRepository.getContatos()

                    nome = ""
                    email = ""
                    telefone = ""
                    nascimento = ""
                    cep = ""
                    bairro = ""
                    logradouro = ""
                    numero = ""
                    estado = ""
                    cidade = ""
                    alterar = false
                    idAlterando = null
                }
            }
        ) { Text(if (!alterar) "Cadastrar" else "Alterar") }
        }

        // ********** LISTAGEM DE CONTATOS
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Contatos Cadastrados",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(0.4f)
        ) {
            items(contatos) { contato ->

                // Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        Column {
                            Text("Nome: ${contato.nome}")
                            Text("E-mail: ${contato.email}")
                            Text("Telefone: ${contato.telefone}")
                            Text("Nascimento: ${contato.nascimento}")
                            Text("CEP: ${contato.cep}")
                            Text("Bairro: ${contato.bairro}")
                            Text("Logradouro: ${contato.logradouro}")
                            Text("Número: ${contato.numero}")
                            Text("Estado: ${contato.estado}")
                            Text("Cidade: ${contato.cidade}")
                        }

                        Row(modifier = Modifier.align(Alignment.TopEnd)) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        contato.id?.let {
                                            val contatoAlterado = contatoRepository.getById(it)
                                            idAlterando = it
                                            nome = contatoAlterado.nome
                                            email = contatoAlterado.email
                                            telefone = contatoAlterado.telefone
                                            nascimento = contatoAlterado.nascimento
                                            cep = contatoAlterado.cep
                                            bairro = contatoAlterado.bairro
                                            logradouro = contatoAlterado.logradouro
                                            numero = contatoAlterado.numero
                                            estado = contatoAlterado.estado
                                            cidade = contatoAlterado.cidade
                                            alterar = true
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Alterar"
                                )
                            }

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        contato.id?.let {
                                            contatoRepository.removeContato(it)
                                            contatos = contatoRepository.getContatos()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remover"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}