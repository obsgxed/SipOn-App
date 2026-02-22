package com.example.siponapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SipOnGame()
            }
        }
    }
}

@Composable
fun SipOnGame() {
    val questions = listOf(
        "Пьет тот, кто последний проверял соцсети 📱",
        "Никогда не танцевал на барной стойке? Пей! 💃",
        "Пьют все, кто в белых кроссовках 👟",
        "Тот, кто выше всех за столом, делает 2 глотка 🦒",
        "Расскажи самый стыдный случай из школы или пей moy xuy🙊"
    )

    var currentQuestion by remember { mutableStateOf("Готовы к SipOn?") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = currentQuestion,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )

        Button(
            onClick = { currentQuestion = questions.random() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("СЛЕДУЮЩИЙ ГЛОТОК 🍻", fontSize = 18.sp)
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SipOnGamePreview() {
    MaterialTheme {
        Surface {
            SipOnGame()
        }
    }
}