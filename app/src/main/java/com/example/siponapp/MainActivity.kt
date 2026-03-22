package com.example.siponapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


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
    var playerNames by remember {mutableStateOf(listOf<String>())}
    when (currentScreen) {
        "start" -> StartScreen(onStartClicked={currentScreen="setup"})
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SipOnGamePreview() {
    MaterialTheme {
        Surface {
            SipOnGame(playerNames=emptyList(), onBackClicked = {})
        }
    }
}
