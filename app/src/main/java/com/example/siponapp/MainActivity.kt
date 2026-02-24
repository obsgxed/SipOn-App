package com.example.siponapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Navigator()
            }
        }
    }
}

@Composable

fun Navigator () {

    var currentScreen by remember {mutableStateOf("start")}
    when (currentScreen) {
        "start" -> StartScreen(onStartClicked={currentScreen="game"})
        "setup" -> Players(
            onBackClicked={currentScreen="start"},
            onPlayersConfirmed={names->
                playerNames=names
                currentScreen="game"
        }
    )
        "game" -> SipOnGame(
            playerNames=playerNames,
            onBackClicked = {currentScreen="setup"}
        )

    }

}


@Composable
fun StartScreen(onStartClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SipOn",
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onStartClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            Text("START GAME", fontSize = 20.sp)
        }
    }
}


@Composable
fun Players(onBackClicked:() -> Unit, onPlayersConfirmed:(List<String>) -> Unit) {

    var newName by remember {mutableStateOf("")}
    val names = remember{mutableStateListOf<String>()}

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick= onBackClicked, modifier = Modifier.align(Alignment.Start)) {
            Text("<-")
        }
        Text("Who is playing?", fontSize=32.sp, modifier=Modifier.padding (vertical=16.dp))
        Row (
            modifier=Modifier.fillMaxWidth(),
            verticalAlignment=Alignment.CenterVertically
    ) {
            OutlinedTextField(
                value=newName,
                onValueChange={newName=it},
                label={Text("Player Name")},
                modifier=Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newName.isNotBlank()) {
                        names.add(newName)
                        newName = "" // Clear the input field
                    }
                }
            ) {
                Text("Add")
            }
        }

        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(vertical = 16.dp)) {
            items(names) { name ->
                Text("• $name", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
            }
        }

        Button(
            enabled = names.isNotEmpty(),
            onClick = { onPlayersConfirmed(names.toList()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            Text("READY STEADY GO BLYADI", fontSize = 20.sp)
        }
    }
}


@Composable
fun SipOnGame(onBackClicked: () -> Unit) {
    val generativeModel = remember{
        GenerativeModel(
            modelName = "gemini-3-flash-preview",
            apiKey = "AIzaSyDkyOU4WtMex3FUCK1qqHuNJ5MP8tiAEBY"
        )
    }

    val playersString=playerNames.joinToString(", ")
    val scope = rememberCoroutineScope()
    var currentQuestion by remember { mutableStateOf("Lets see who is drinking now!") }
    var isLoading by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = onBackClicked,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("← BACK TO MENU")
        }

        Text(
            text = if (isLoading) "Loading..." else currentQuestion,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )


        Button(
            enabled = !isLoading,
            onClick = {
                isLoading = true
                scope.launch {
                    try {
                        val response = generativeModel.generateContent("System: You are a game engine for \"SipOn\".Context: Players are: [$playersString] pick one person from this string. Current intensity: [1-10].Task: Generate ONE social challenge.Format: \"[name_person]: takes [1-5] sips if [Question]\"Difficulty Logic: If intensity < 3: Light ice-breakers. If intensity 4-7: Personal/spicy. If intensity > 8: Deep 18+ adult themes.Constraint: NO intro, NO emojis, NO bold text. Just the raw string.")
                        currentQuestion = response.text ?: "Try again!"
                    } catch (e: Exception) {
                        currentQuestion = "Error: ${e.localizedMessage}"
                    }
                    isLoading = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
            } else {
                Text("Fetch your question", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SipOnGamePreview() {
    MaterialTheme {
        Surface {
            SipOnGame(onBackClicked = {})
        }
    }
}