package com.example.solveexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathQuizApp()
        }
    }
}

@Composable
fun MathQuizApp() {
    var num1 by remember { mutableStateOf(0) }
    var num2 by remember { mutableStateOf(0) }
    var operator by remember { mutableStateOf("") }
    var correctAnswers by remember { mutableStateOf(0) }
    var wrongAnswers by remember { mutableStateOf(0) }
    var totalAnswers by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(56.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Всего решено: $totalAnswers",
            fontSize = 25.sp
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 15.dp, 10.dp, 25.dp)
        ) {
            Text(
                text = "Правильно:\n " + " " + "$correctAnswers ",
                fontSize = 20.sp,
                modifier = Modifier
            )
            Text(text = " ")
            Text(
                text = " Неправильно:\n" + " " + "$wrongAnswers",
                fontSize = 20.sp
            )
        }
        Text(
            text = " ${
                if (totalAnswers == 0) "0.00%" else round(100 * correctAnswers.toDouble() / totalAnswers * 100) / 100
            }%",
            fontSize = 35.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Решите пример: $num1 $operator $num2 = ?",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        TextField(
            value = userAnswer,
            onValueChange = {
                if (it.text.length > 15) {
                    userAnswer = TextFieldValue(it.text.take(2))
                } else {
                    userAnswer = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Ваш ответ") },
            modifier = Modifier.width(100.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {
                num1 = (10..99).random()
                num2 = (10..99).random()
                operator = listOf("*", "-", "+").random()
                totalAnswers++
                userAnswer = TextFieldValue("")
            },
            enabled = userAnswer.text.isEmpty()
        ) {
            Text(
                text = "Старт",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            val answerCorrect = checkAnswer(userAnswer.text, num1, num2, operator)

            Button(
                onClick = {
                    if (userAnswer.text.isNotEmpty()) {
                        if (answerCorrect) {
                            correctAnswers++
                        } else {
                            wrongAnswers++
                        }
                        userAnswer = TextFieldValue("")
                    }
                },
                enabled = userAnswer.text.length >= 1
            ) {
                Text(
                    text = "Проверить",
                    fontSize = 20.sp
                )
            }
        }
    }
}

fun checkAnswer(userAnswer: String, num1: Int, num2: Int, operator: String): Boolean {
    val correctAnswer = when(operator) {
        "*" -> (num1 * num2).toString()
        "-" -> (num1 - num2).toString()
        "+" -> (num1 + num2).toString()
        else -> ""
    }
    return userAnswer == correctAnswer
}

@Preview(showBackground = true)
@Composable
fun SolveExample() {
    MaterialTheme {
        MathQuizApp()
    }
}