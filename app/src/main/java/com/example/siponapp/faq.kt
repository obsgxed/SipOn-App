package com.example.siponapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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

        RuleCard(
            iconRes = R.drawable.people,
            number = "01",
            title = "Add all players",
            description = "Enter every player's name before starting the game"
        )

        RuleCard(
            iconRes = R.drawable.ironcan,
            number = "02",
            title = "Get challenged",
            description = "Each round a random player is picked and given a challenge"
        )

        RuleCard(
            iconRes = R.drawable.ironcan2,
            number = "03",
            title = "Sip or complete",
            description = "Complete the challenge or take the required number of sips"
        )

        RuleCard(
            iconRes = R.drawable.icecube,
            number = "04",
            title = "Stay honest",
            description = "Lying or skipping means you drink double — no exceptions!"
        )

        RuleCard(
            iconRes = R.drawable.cancelcircle,
            number = "05",
            title = "Drink responsibly",
            description = "Know your limits. Never drink and drive. Stay safe and have fun!"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStartClicked,
            modifier = Modifier.fillMaxWidth().height(65.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE6D50))
        ) {
            Text("Let's go!", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun RuleCard(iconRes: Int, number: String, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF8A0045)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFEE6D50), shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(number, color = Color(0xFFEE6D50), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, color = Color(0xFFE0B8A0), fontSize = 14.sp)
            }
        }
    }
}