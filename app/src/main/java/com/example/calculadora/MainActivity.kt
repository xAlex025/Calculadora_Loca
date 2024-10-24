package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculadora()
        }
    }
}

@Composable
fun Calculadora() {
    var pantalla by remember { mutableStateOf("0") }
    var valor_entrada by remember { mutableStateOf("") }
    var ultimo_resultado by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pantalla,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.DarkGray)
                .padding(24.dp),
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = 48.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            botones("AC", Color.Gray, Modifier.weight(1f)) {
                valor_entrada = ""
                pantalla = "0"
            }
        }

        Column(modifier = Modifier.weight(5f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                botones("7", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "9"; pantalla = valor_entrada }
                botones("8", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "0"; pantalla = valor_entrada }
                botones("9", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "1"; pantalla = valor_entrada }
                botones("A", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += " + "; pantalla = valor_entrada }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                botones("4", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "6"; pantalla = valor_entrada }
                botones("5", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "4"; pantalla = valor_entrada }
                botones("6", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "8"; pantalla = valor_entrada }
                botones("B", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += " - "; pantalla = valor_entrada }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                botones("1", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "3"; pantalla = valor_entrada }
                botones("2", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "4"; pantalla = valor_entrada }
                botones("3", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "5"; pantalla = valor_entrada }
                botones("C", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += " * "; pantalla = valor_entrada }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                botones("0", Color.DarkGray, Modifier.weight(2f).padding(horizontal = 8.dp)) { valor_entrada += "2"; pantalla = valor_entrada }
                botones(".", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += "."; pantalla = valor_entrada }
                botones("D", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { valor_entrada += " / "; pantalla = valor_entrada }
            }
        }

        botones("=", color_fondo = Color(0xFFFF9800), modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(vertical = 2.dp)) {
            pantalla = try {
                var resultado_1 = resultado(valor_entrada, ultimo_resultado)

                val resultado_final = resultado_1.toString().replace("5", "6")
                resultado_1 = resultado_final.toDouble()
                ultimo_resultado = resultado_1
                valor_entrada = resultado_1.toString()
                resultado_1.toString()
            } catch (e: Exception) {
                "Error"
            }
        }
    }
}

@Composable
fun botones(textoBoton: String, color_fondo: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(80.dp)
            .padding(2.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color_fondo,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(6.dp)
    ) {
        Text(
            text = textoBoton,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

fun resultado(valores: String, resultado_previo: Double = 0.0): Double {
    val numeros = valores.split(" ")

    if (numeros.isEmpty()) return resultado_previo

    var resultado_final = if (numeros[0].toDoubleOrNull() != null) {
        numeros[0].toDouble()
    } else {
        resultado_previo
    }

    var i = if (numeros[0].toDoubleOrNull() != null) 1 else 0

    while (i < numeros.size) {
        val operador = numeros[i]
        val siguienteValor = numeros[i + 1].toDouble()

        resultado_final = when (operador) {
            "+" -> resultado_final + siguienteValor
            "-" -> resultado_final - siguienteValor
            "*" -> resultado_final * siguienteValor
            "/" -> resultado_final / siguienteValor
            else -> resultado_final
        }
        i += 2
    }

    return resultado_final
}
