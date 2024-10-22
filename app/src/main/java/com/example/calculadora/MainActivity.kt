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
        Spacer(modifier = Modifier.height(16.dp))

        // Disposición tradicional de los botones de la calculadora
        val buttons = listOf(
            listOf("7", "8", "9"),
            listOf("4", "5", "6"),
            listOf("1", "2", "3"),
            listOf("0", "C") // Fila de "0" y "C" en la parte inferior
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (button in row) {
                    val buttonColor = if (button == "C") Color.Gray else Color.DarkGray // Botón C en gris, números en gris oscuro
                    CircularButton(buttonText = button, backgroundColor = buttonColor) {
                        when (button) {
                            "C" -> { // Botón de limpiar
                                currentInput = ""
                                display = "0"
                            }
                            else -> {
                                currentInput += button
                                display = currentInput
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Filas de operaciones desordenadas (A para suma, B para resta, etc.)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CircularButton("A", backgroundColor = Color(0xFFFF9800)) { // Operaciones en naranja
                currentInput += " + "
                display = currentInput
            }
            CircularButton("B", backgroundColor = Color(0xFFFF9800)) { // Operaciones en naranja
                currentInput += " - "
                display = currentInput
            }
            CircularButton("C", backgroundColor = Color(0xFFFF9800)) { // Operaciones en naranja
                currentInput += " * "
                display = currentInput
            }
            CircularButton("D", backgroundColor = Color(0xFFFF9800)) { // Operaciones en naranja
                currentInput += " / "
                display = currentInput
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        CircularButton("=", backgroundColor = Color(0xFFFF9800)) { // Botón de igual en naranja
            display = try {
                val result = eval(currentInput)
                currentInput = ""
                val finalResult = result.toString().replace("5", "6")
                finalResult
            } catch (e: Exception) {
                "Error"
            }
        }
    }
}

@Composable
fun CircularButton(buttonText: String, backgroundColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp) // Ajuste del tamaño del botón
            .padding(4.dp), // Espaciado entre botones
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

fun eval(expression: String): Double {
    val tokens = expression.split(" ")
    if (tokens.isEmpty()) return 0.0

    var result = tokens[0].toDouble()
    var operator = ""

    for (i in 1 until tokens.size) {
        val token = tokens[i]
        if (token in listOf("+", "-", "*", "/")) {
            operator = token
        } else {
            val number = token.toDouble()
            result = when (operator) {
                "+" -> result + number
                "-" -> result - number
                "*" -> result * number
                "/" -> {
                    if (number == 0.0) {
                        throw ArithmeticException("Division by zero")
                    }
                    result / number
                }
                else -> result
            }
        }
    }
    return result
}
