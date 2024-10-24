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
            CrazyCalculatorApp()
        }
    }
}

@Composable
fun CrazyCalculatorApp() {
    var display by remember { mutableStateOf("0") }
    var currentInput by remember { mutableStateOf("") }
    var lastResult by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo oscuro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display de la calculadora
        Text(
            text = display,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.DarkGray)
                .padding(24.dp),
            textAlign = TextAlign.End,
            color = Color.White, // Texto blanco
            fontSize = 48.sp
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp), // Reducimos el espacio entre el botón "C" y los números
            horizontalArrangement = Arrangement.Start
        ) {
            CircularButton("AC", Color.Gray, Modifier.weight(1f)) { // Botón C en gris
                currentInput = ""
                display = "0"
            }
        }

        // Filas de números y operaciones combinadas
        Column(modifier = Modifier.weight(5f)) { // Usamos weight aquí para asegurarnos de que las filas ocupen espacio pero no todo
            // Primera fila: 7, 8, 9, A (Suma)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribuimos espacio uniformemente
            ) {
                CircularButton("7", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "9"; display = currentInput }
                CircularButton("8", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "0"; display = currentInput }
                CircularButton("9", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "1"; display = currentInput }
                CircularButton("A", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += " + "; display = currentInput } // Suma
            }

            // Segunda fila: 4, 5, 6, B (Resta)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribuimos espacio uniformemente
            ) {
                CircularButton("4", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "6"; display = currentInput }
                CircularButton("5", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "4"; display = currentInput }
                CircularButton("6", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "8"; display = currentInput }
                CircularButton("B", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += " - "; display = currentInput } // Resta
            }

            // Tercera fila: 1, 2, 3, C (Multiplicación)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribuimos espacio uniformemente
            ) {
                CircularButton("1", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "3"; display = currentInput }
                CircularButton("2", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "4"; display = currentInput }
                CircularButton("3", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "5"; display = currentInput }
                CircularButton("C", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += " * "; display = currentInput } // Multiplicación
            }

            // Cuarta fila: 0, ., D (División)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribuimos espacio uniformemente
            ) {
                CircularButton("0", Color.DarkGray, Modifier.weight(2f).padding(horizontal = 8.dp)) { currentInput += "2"; display = currentInput }
                CircularButton(".", Color.DarkGray, Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += "."; display = currentInput }
                CircularButton("D", Color(0xFFFF9800), Modifier.weight(1f).padding(horizontal = 8.dp)) { currentInput += " / "; display = currentInput } // División
            }
        }

        // Botón de calcular el resultado
        CircularButton("=", backgroundColor = Color(0xFFFF9800), modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(vertical = 2.dp)) { // Botón de igual en naranja
            display = try {
                var result = eval(currentInput, lastResult) // Usa el último resultado si es necesario

                val finalResult = result.toString().replace("5", "6")
                result = finalResult.toDouble()
                lastResult = result // Guarda el último resultado para continuar calculando
                currentInput = result.toString() // Muestra el resultado en el input para continuar
                result.toString()
            } catch (e: Exception) {
                "Error"
            }
        }
    }
}

@Composable
fun CircularButton(buttonText: String, backgroundColor: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(80.dp) // Ajuste del tamaño del botón
            .padding(2.dp), // Reducimos el espacio entre botones
        shape = CircleShape, // Forma circular
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(6.dp)
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

fun eval(expression: String, previousResult: Double = 0.0): Double {
    val tokens = expression.split(" ")

    // Si no hay tokens (expresión vacía), devolvemos el resultado previo
    if (tokens.isEmpty()) return previousResult

    // Determinar si la expresión empieza con un número o un operador
    var result = if (tokens[0].toDoubleOrNull() != null) {
        tokens[0].toDouble()
    } else {
        previousResult
    }

    var i = if (tokens[0].toDoubleOrNull() != null) 1 else 0

    // Evaluar el resto de la expresión
    while (i < tokens.size) {
        val operator = tokens[i]
        val nextValue = tokens[i + 1].toDouble()

        result = when (operator) {
            "+" -> result + nextValue
            "-" -> result - nextValue
            "*" -> result * nextValue
            "/" -> result / nextValue
            else -> result
        }
        i += 2
    }

    return result
}

