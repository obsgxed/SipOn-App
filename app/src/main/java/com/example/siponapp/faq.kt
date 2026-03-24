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
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun SipOnStart(onStartClicked: () -> Unit, onBackClicked: () -> Unit) {
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
            text = "RULES",
            fontSize = 48.sp,
            fontFamily = Inter,
            color = Color(0xFFF0D5C0)
        )
        Spacer(modifier = Modifier.height(16.dp))

    Card (
        modifier=Modifier.fillMaxWidth().padding(16.dp),
        colors=CardDefaults.cardColors(containerColor=Color(0xFF8A0045)),
        shape=RoundedCornerShape(16.dp)
    ) {
         Row(
             modifier = Modifier.padding(16.dp),
             verticalAlignment=Alignment.Top
         ) {
             Box(
                 modifier=Modifier
                    .size(48.dp)
                    .background(Color(0xFFEE6D50), shape=RoundedCornerShape(50)),
                contentAlignment=Alignment.Center
             ) {
                 Icon(
                     painter=painterResource(id=R.drawable.people),
                     contentDescription=null,
                     tint=Color.Unspecified,
                     modifier=Modifier.size(24.dp)
                 )
             }
            Spacer(modifier=Modifier.width(16.dp))
            Column {
                Text("How to play?",color=Color.White,fontSize=16.sp)
                Spacer(modifier=Modifier.height(4.dp))
                Text("Add all players", color=Color.White, fontSize=16.sp)
         }
    }
}
        Text(
            text = "• Someone gets picked\n• They answer or sip\n• Have fun!",
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f).wrapContentHeight()
        )

        Button(
            onClick = onStartClicked,
            modifier = Modifier.fillMaxWidth().height(65.dp)
        ) {
            Text("Let's go!", fontSize = 18.sp)
        }
    }
}