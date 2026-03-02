package com.example.siponapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

val ComicSans = FontFamily(
    Font(R.font.comicsans, FontWeight.Bold)
)

val Atop= FontFamily(
    Font(R.font.atop, FontWeight.Normal),
)



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


@Composable
fun StartScreen(onStartClicked: () -> Unit) {
    val context =LocalContext.current
    val imageLoader=coil.ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT>=28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(modifier= Modifier.fillMaxSize()) {
        AsyncImage(
            model= ImageRequest.Builder(context)
                .data(R.drawable.gifstartingscreen)
                .crossfade(true)
                .build(),
            contentDescription=null,
            imageLoader=imageLoader,
            modifier= Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Black.copy(alpha=0.4f))
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(modifier=Modifier.height(100.dp),
            text = "SIP",
            fontFamily=Atop,
            fontSize = 72.sp,
            textAlign = TextAlign.Center,
            color=androidx.compose.ui.graphics.Color(0xFF7a003d)
        )
        Text(modifier=Modifier.height(100.dp),
            text = "ON",
            fontFamily=Atop,
            fontSize = 72.sp,
            textAlign = TextAlign.Center,
            color=androidx.compose.ui.graphics.Color(0xFFf63e50)
        )
        Spacer(modifier = Modifier.height(150.dp))
        Button(
            onClick = onStartClicked,
            modifier = Modifier
                .height(75.dp)
                .padding(bottom=25.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor=androidx.compose.ui.graphics.Color(0xFFee6d50),
                contentColor=androidx.compose.ui.graphics.Color.White
            )
        ) {
            Text(
                text="Start the party",
                fontFamily=ComicSans,
                fontSize=22.sp,
                color=androidx.compose.ui.graphics.Color(0xFFf0d5c0)
            )


            }
        }
    }
}

@Composable
fun Players(onBackClicked:() -> Unit, onPlayersConfirmed:(List<String>) -> Unit) {

    var newName by remember {mutableStateOf("")}
    val names = remember{mutableStateListOf<String>()}

    Box (
        modifier=Modifier
            .fillMaxSize()
            .background(Color(0xFF7A003D))

    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick= onBackClicked,
            modifier = Modifier.align(Alignment.Start)
                .size(84.dp)

        ) {
            Icon(
                painter=painterResource(id=R.drawable.icecube),
                contentDescription="Back Button",
                tint=Color.Unspecified
            )
        }
        Text("Players", fontSize=64.sp, modifier=Modifier.padding (vertical=48.dp),fontFamily=ComicSans,color=Color(0xFFEE6D50))
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
                        newName = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            itemsIndexed(names) {index,name ->
                var isVisible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    isVisible = true
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(500)) + expandVertically(),
                    exit = fadeOut()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF57FF7C)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                        Text(
                            text = name,
                            fontSize = 24.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontFamily = ComicSans
                        )
                        IconButton(
                            onClick={names.removeAt(index)},
                            modifier=Modifier.size(32.dp)

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.cancelcircle),
                                contentDescription = "Remove",
                                tint = Color(0xFFFD0A0A)
                            )
                        }
                    }
                }
            }
        }
            }
        IconButton(
            enabled = names.isNotEmpty(),
            onClick = { onPlayersConfirmed(names.toList()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Icon(
                painter = painterResource(id=R.drawable.ironcan),
                contentDescription="Confirm",
                tint=Color.Unspecified
            )
        }
    }
}


@Composable
fun SipOnGame(playerNames:List<String>, onBackClicked: () -> Unit) {
    val generativeModel = remember{
        GenerativeModel(
            modelName = "gemini-3-flash-preview",
            apiKey = "AIzaSyDNbvmEn7ZflOFYRhA4n4n6uaoq_Mw6-hY"
        )
    }

    val playersString=playerNames.joinToString(", ")
    val scope = rememberCoroutineScope()
    var currentQuestion by remember { mutableStateOf("Lets see who is drinking now!") }
    var isLoading by remember { mutableStateOf(false) }

    Box (
        modifier=Modifier
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
            modifier = Modifier.align(Alignment.Start)
                .size(84.dp)
        ) {
            Icon (
                painter = painterResource(id = R.drawable.backbuttonicecube),
                contentDescription = "Back",
                tint=Color.Unspecified
            )
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
                        val response = generativeModel.generateContent("System: You are a game engine for \"SipOn\".Context: Players are: [$playerNames] pick one person from this string. Current intensity: [1-10].Task: Generate ONE social challenge.Format: \"[name_person]: takes [1-5] sips if [Question]\"Difficulty Logic: If intensity < 3: Light ice-breakers. If intensity 4-7: Personal/spicy. If intensity > 8: Deep 18+ adult themes.Constraint: NO intro, NO emojis, NO bold text. Just the raw string.")
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
            SipOnGame(playerNames=emptyList(), onBackClicked = {})
        }
    }
}
