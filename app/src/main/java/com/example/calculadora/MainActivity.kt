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

        // Botón C en la parte superior izquierda
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), // Espacio entre el botón "C" y los números
            horizontalArrangement = Arrangement.Start
        ) {
            CircularButton("C", Color.Gray) { // Botón C en gris
                currentInput = ""
                display = "0"
            }
        }

        // Filas principales de botones
        Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
            // Botones de números a la izquierda
            Column(modifier = Modifier.weight(3f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircularButton("7", Color.DarkGray) {
                        currentInput += "7"
                        display = currentInput
                    }
                    CircularButton("8", Color.DarkGray) {
                        currentInput += "8"
                        display = currentInput
                    }
                    CircularButton("9", Color.DarkGray) {
                        currentInput += "9"
                        display = currentInput
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircularButton("4", Color.DarkGray) {
                        currentInput += "4"
                        display = currentInput
                    }
                    CircularButton("5", Color.DarkGray) {
                        currentInput += "5"
                        display = currentInput
                    }
                    CircularButton("6", Color.DarkGray) {
                        currentInput += "6"
                        display = currentInput
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircularButton("1", Color.DarkGray) {
                        currentInput += "1"
                        display = currentInput
                    }
                    CircularButton("2", Color.DarkGray) {
                        currentInput += "2"
                        display = currentInput
                    }
                    CircularButton("3", Color.DarkGray) {
                        currentInput += "3"
                        display = currentInput
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircularButton("0", Color.DarkGray, Modifier.weight(2f)) {
                        currentInput += "0"
                        display = currentInput
                    }
                    CircularButton(".", Color.DarkGray, Modifier.weight(1f)) {
                        currentInput += "."
                        display = currentInput
                    }
                }
            }

            // Botones de operaciones a la derecha
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly // Espaciado ajustado
            ) {
                CircularButton("A", Color(0xFFFF9800)) { // Suma
                    currentInput += " + "
                    display = currentInput
                }
                CircularButton("B", Color(0xFFFF9800)) { // Resta
                    currentInput += " - "
                    display = currentInput
                }
                CircularButton("C", Color(0xFFFF9800)) { // Multiplicación
                    currentInput += " * "
                    display = currentInput
                }
                CircularButton("D", Color(0xFFFF9800)) { // División
                    currentInput += " / "
                    display = currentInput
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de calcular el resultado
        CircularButton("=", backgroundColor = Color(0xFFFF9800), modifier = Modifier.fillMaxWidth()) { // Botón de igual en naranja
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
fun CircularButton(buttonText: String, backgroundColor: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
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

// Eval function to calculate the result
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
