package com.example.siponapp

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp







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
                },
                colors=ButtonDefaults.buttonColors(
                    containerColor=Color(0xFFEE6D50),
                    contentColor=Color.White
                )
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
