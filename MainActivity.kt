package com.example.mathgame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.PreviewParameter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathGameApp()
        }
    }
}

@Composable
fun MathGameApp() {
    var currentTurn by remember { mutableStateOf(1) }
    var currentProblem by remember { mutableStateOf(generateProblem()) }
    var userAnswer by remember { mutableStateOf(TextFieldValue()) }
    var isGameOver by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    // Check if the game is over
    if (isGameOver) {
        GameOverScreen(
            score = score,
            onRestart = {
                currentTurn = 1
                score = 0
                isGameOver = false
                currentProblem = generateProblem()
                userAnswer = TextFieldValue()
            }
        )
    } else {
        // Main Game Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Turn: $currentTurn", style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp))
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Solve: ${currentProblem.first} + ${currentProblem.second}", style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = { Text("Enter Answer") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val correctAnswer = currentProblem.first + currentProblem.second
                    if (userAnswer.text.toIntOrNull() == correctAnswer) {
                        score++
                    }

                    if (currentTurn < 3) {
                        currentTurn++
                        currentProblem = generateProblem()
                        userAnswer = TextFieldValue()
                    } else {
                        isGameOver = true
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun GameOverScreen(score: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Game Over!", style = androidx.compose.ui.text.TextStyle(fontSize = 30.sp))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Your score: $score", style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRestart) {
            Text("Restart")
        }
    }
}

fun generateProblem(): Pair<Int, Int> {
    val num1 = (1..10).random()
    val num2 = (1..10).random()
    return Pair(num1, num2)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MathGameApp()
}
