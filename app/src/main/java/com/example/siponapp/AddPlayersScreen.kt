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
import androidx.compose.ui.text.style.TextAlign





@Composable
fun Players(onBackClicked: () -> Unit, onPlayersConfirmed: (List<String>) -> Unit) {

    var newName by remember { mutableStateOf("") }
    val names = remember { mutableStateListOf<String>() }
    val playerCount = names.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7A003D))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .align(Alignment.Start)
                    .size(84.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icecube),
                    contentDescription = "Back Button",
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "PLAYERS",
                fontSize = 48.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 25.dp),
                fontFamily = Inter,
                color = Color(0xFFF0D5C0),
                textAlign = TextAlign.Start
            )

            TextField(
                value = newName,
                onValueChange = { newName = it },
                placeholder = {
                    Text(
                        "Enter player name...",
                        color = Color(0xFFAA707A),
                        fontFamily = Inter,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEED7C5),
                    unfocusedContainerColor = Color(0xFFEED7C5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFFEE6D50)
                ),
                trailingIcon = {
                    Button(
                        onClick = {
                            if (newName.isNotBlank()) {
                                names.add(newName)
                                newName = ""
                            }
                        },
                        modifier = Modifier
                            .height(46.dp)
                            .padding(end = 12.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEE6D50),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Add", fontFamily = Inter)
                    }
                },
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
            ) {
                itemsIndexed(names) { index, name ->
                    var isVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) { isVisible = true }

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
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFEE6D50))
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
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(3f),
                                    fontFamily = Inter
                                )
                                IconButton(
                                    onClick = { names.removeAt(index) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.bin),
                                        contentDescription = "Remove",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.people),
                    contentDescription = null,
                    tint = Color(0xFFBA969B),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$playerCount PLAYERS IN PARTY",
                    color = Color(0xFFBA969B),
                    fontFamily = Inter,
                    fontSize = 14.sp,
                )
            }

            IconButton(
                enabled = names.isNotEmpty(),
                onClick = { onPlayersConfirmed(names.toList()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ironcan2),
                    contentDescription = "Confirm",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

