package com.example.siponapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

@Composable
fun SipOnGame(playerNames: List<String>, onBackClicked: () -> Unit) {
    val generativeModel = remember {
        GenerativeModel(
            modelName = "gemini-3-flash-preview",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

    val scope = rememberCoroutineScope()
    var gameStarted by remember { mutableStateOf(false) }
    var currentQuestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    fun fetchQuestion() {
        isLoading = true
        scope.launch {
            try {
                val response = generativeModel.generateContent("System: You are a game engine for \"SipOn\".Context: Players are: [$playerNames] pick one person from this string. Current intensity: [1-10].Task: Generate ONE social challenge.Format: \"[name_person]: takes [1-5] sips if [Question]\"Difficulty Logic: If intensity < 3: Light ice-breakers. If intensity 4-7: Personal/spicy. If intensity > 8: Deep 18+ adult themes.Constraint: NO intro, NO emojis, NO bold text. Just the raw string.")
                currentQuestion = response.text ?: "Try again!"
            } catch (e: Exception) {
                currentQuestion = "Error: ${e.localizedMessage}"
            }
            isLoading = false
        }
    }

    if (!gameStarted) {
        SipOnStart(
            onStartClicked = {
                gameStarted = true
                fetchQuestion()  // сразу грузим первый вопрос
            },
            onBackClicked = onBackClicked
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF7A003D))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier.align(Alignment.Start).size(84.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.backbuttonicecube),
                    contentDescription = "Back",
                    tint = Color.Unspecified
                )
            }

            Text(
                text = if (isLoading) "Loading..." else currentQuestion,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.weight(1f).wrapContentHeight()
            )

            Button(
                enabled = !isLoading,
                onClick = { fetchQuestion() },
                modifier = Modifier.fillMaxWidth().height(65.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Next question", fontSize = 18.sp)
                }
            }
        }
    }
}